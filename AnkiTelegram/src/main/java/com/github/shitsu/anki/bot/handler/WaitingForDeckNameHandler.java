package com.github.shitsu.anki.bot.handler;

import com.github.shitsu.anki.bot.State;
import com.github.shitsu.anki.entity.UserContext;
import com.github.shitsu.anki.entity.UserEntity;
import com.github.shitsu.anki.sevice.UserContextService;
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
import java.util.List;
import java.util.Locale;

import static com.github.shitsu.anki.bot.handler.AddingDeckHandler.*;
import static com.github.shitsu.anki.util.TelegramUtil.createMessageTemplate;
import static com.github.shitsu.anki.util.TelegramUtil.createInlineKeyboardButton;



@Component
@RequiredArgsConstructor
@Slf4j
public class WaitingForDeckNameHandler implements Handler {
    private final UserService userService;
    private final MessageSource messageSource;
    private final UserContextService userContextService;


    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String message, Locale locale) {
        if (message.startsWith("/") || message.trim().isEmpty()) {
            SendMessage errorMessage = createMessageTemplate(user);
            errorMessage.setText(messageSource.getMessage("bot.invalid.deck.name", null, locale));
            log.error("Invalid Deck Name: {}", message);
            return List.of(errorMessage);
        }

        UserEntity freshUser = userService.findOrCreateUser(user.getChatId(), user.getUsername());
        freshUser.setState(State.ADDING_DECK);
        userService.save(freshUser);

        UserContext context = userContextService.getContext(user.getChatId());
        if (context == null) {
            context = new UserContext();
        }
        context.setTempDeckName(message.trim());
        userContextService.saveContext(user.getChatId(), context);


        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> row = List.of(
                createInlineKeyboardButton("Accept", ADD_DECK_ACCEPT),
                createInlineKeyboardButton("Reset", ADD_DECK_RESET),
                createInlineKeyboardButton("Back", ADD_DECK_BACK)
        );
        inlineKeyboardMarkup.setKeyboard(List.of(row));

        SendMessage response = createMessageTemplate(freshUser);
        response.setText(messageSource.getMessage("bot.accept.deck.name", new Object[]{message}, locale));
        response.setReplyMarkup(inlineKeyboardMarkup);

        return List.of(response);

    }

    @Override
    public List<String> operatedCallBackQuery() {
        return List.of();
    }

    @Override
    public State operatedBotState() {
        return State.WAITING_FOR_DECK_NAME;
    }
}
