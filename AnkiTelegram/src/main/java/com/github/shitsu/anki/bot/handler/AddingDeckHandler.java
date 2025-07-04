package com.github.shitsu.anki.bot.handler;

import com.github.shitsu.anki.bot.State;
import com.github.shitsu.anki.entity.UserEntity;
import com.github.shitsu.anki.sevice.DeckService;
import com.github.shitsu.anki.sevice.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.github.shitsu.anki.util.TelegramUtil.createMessageTemplate;
import static com.github.shitsu.anki.util.TelegramUtil.createInlineKeyboardButton;


@Slf4j
@RequiredArgsConstructor
@Component
public class AddingDeckHandler implements Handler{

    public static final String ADD_DECK_ACCEPT =  "/add_deck_accept";
    public static final String ADD_DECK_RESET =  "/add_deck_reset";
    public static final String ADD_DECK_BACK =  "/add_deck_back";

    private final UserService userService;
    private final DeckService deckService;
    private final MessageSource messageSource;
    private final StartHandler startHandler;


    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String message, Locale locale) {
        return switch (message){
            case ADD_DECK_ACCEPT -> accept(user, locale);
            case ADD_DECK_RESET -> reset(user, locale);
            case ADD_DECK_BACK -> back(user, locale);
            default -> checkName(user, message, locale);
        };
    }

    private List<PartialBotApiMethod<? extends Serializable>> accept(UserEntity user, Locale locale) {
        String deckName = user.getTempDeckName();
        deckService.addDeckToUser(user, deckName);
        user.setTempDeckName(null);
        user.setState(State.START);
        userService.save(user);

        SendMessage sendMessage = createMessageTemplate(user);
        sendMessage.setText(
                messageSource.getMessage("bot.deck.created", new Object[]{deckName}, locale)
        );
        List<PartialBotApiMethod<? extends Serializable>> startMenuMessages =
                startHandler.handle(user, "/start", locale);

        List<PartialBotApiMethod<? extends Serializable>> result = new ArrayList<>();
        result.add(sendMessage);
        result.addAll(startMenuMessages);
        return result;

    }
    private List<PartialBotApiMethod<? extends Serializable>> reset(UserEntity user, Locale locale) {
        user.setTempDeckName(null);
        userService.save(user);

        SendMessage sendMessage = createMessageTemplate(user);
        sendMessage.setText(
                messageSource.getMessage("bot.enter.deck.name", null, locale)
        );
        return List.of(sendMessage);
    }

    private List<PartialBotApiMethod<? extends Serializable>> back(UserEntity user, Locale locale) {
        user.setTempDeckName(null);
        if(!deckService.isAnyDeckExistByUserChatId(user.getChatId())) {
            user.setState(State.START);
        } else{
            user.setState(State.CHOOSING_DECK);
        }
        userService.save(user);

        SendMessage sendMessage = createMessageTemplate(user);
        sendMessage.setText(
                messageSource.getMessage("bot.back.to.deck.menu", null, locale)
        );

        return List.of(sendMessage);
    }

    private List<PartialBotApiMethod<? extends Serializable>> checkName(UserEntity user, String message, Locale locale) {
        user.setTempDeckName(message);
        userService.save(user);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> row = List.of(
                createInlineKeyboardButton("Accept" , ADD_DECK_ACCEPT),
                createInlineKeyboardButton("Reset", ADD_DECK_RESET),
                createInlineKeyboardButton("Back ", ADD_DECK_BACK)
        );


        inlineKeyboardMarkup.setKeyboard(List.of(row));

        SendMessage sendMessage = createMessageTemplate(user);
        sendMessage.setText(
                messageSource.getMessage("bot.accept.deck.name", new Object[]{message}, locale));
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        return List.of(sendMessage);

    }

    @Override
    public State operatedBotState() {
        return State.ADDING_DECK;
    }
    @Override
    public List<String> operatedCallBackQuery() {
        return List.of(ADD_DECK_ACCEPT
                , ADD_DECK_RESET
                , ADD_DECK_BACK);
    }
}
