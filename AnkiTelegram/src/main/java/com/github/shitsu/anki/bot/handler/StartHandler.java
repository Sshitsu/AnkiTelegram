package com.github.shitsu.anki.bot.handler;

import com.github.shitsu.anki.bot.State;
import com.github.shitsu.anki.builder.DeckMessageBuilder;
import com.github.shitsu.anki.entity.UserEntity;
import com.github.shitsu.anki.sevice.DeckService;
import com.github.shitsu.anki.sevice.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

import static com.github.shitsu.anki.util.TelegramUtil.createInlineKeyboardButton;
import static com.github.shitsu.anki.util.TelegramUtil.createMessageTemplate;

@Slf4j
@RequiredArgsConstructor
@Component
public class StartHandler implements Handler {

    @Value("${telegram.bot.username}")
    private String botName;

    private final String CHOSE_DECK = "/choose_deck";
    private final String ADD_DECK = "/add_deck";
    private final String START = "/start";

    private final MessageSource messageSource;
    private final UserService userService;
    private final DeckMessageBuilder deckMessageBuilder;


    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String message, Locale locale) {
        log.info("StartHandler.handle() called with message: {}", message);
        user.setState(State.START);
        userService.save(user);
        return switch (message){
            case CHOSE_DECK -> choose(user, locale);
            case ADD_DECK -> addDeck(user, locale);
            case START -> start(user, locale);
            default -> wrongOperations(user, locale);
        };
    }

    private List<PartialBotApiMethod<? extends Serializable>> start(UserEntity user, Locale locale) {
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(createInlineKeyboardButton(
                messageSource.getMessage("button.start.choose.deck", null, locale),
                CHOSE_DECK
        ));
        buttons.add(createInlineKeyboardButton(
                messageSource.getMessage("button.start.add.deck", null, locale),
                ADD_DECK
        ));

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(List.of(buttons));

        SendMessage actionMessage = createMessageTemplate(user);
        actionMessage.setText(messageSource.getMessage("bot.start.choose.or.add", null, locale));
        actionMessage.setReplyMarkup(markup);

        return List.of(actionMessage);
    }

    private List<PartialBotApiMethod<? extends Serializable>> choose(UserEntity user, Locale locale) {
        user.setState(State.CHOOSING_DECK);
        userService.save(user);

        return deckMessageBuilder.buildDeckListMessage(user, locale);
    }
    private List<PartialBotApiMethod<? extends Serializable>> addDeck(UserEntity user, Locale locale) {
        user.setState(State.WAITING_FOR_DECK_NAME);
        userService.save(user);

        SendMessage addDeckMessage = createMessageTemplate(user);
        addDeckMessage.setText(messageSource.getMessage("bot.add.deck", null, locale));
        return List.of(addDeckMessage);
    }
    private List<PartialBotApiMethod<? extends Serializable>> wrongOperations(UserEntity user, Locale locale) {
        SendMessage wrongOperationMessage = createMessageTemplate(user);
        wrongOperationMessage.setText(messageSource.getMessage("bot.wrong.operations", null, locale));
        return List.of(wrongOperationMessage);
    }



    @Override
    public List<String> operatedCallBackQuery() {
        return List.of(CHOSE_DECK, ADD_DECK);
    }

    @Override
    public State operatedBotState() {
        return State.START;
    }
}
