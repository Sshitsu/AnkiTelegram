package com.github.shitsu.anki.builder;

import com.github.shitsu.anki.entity.UserEntity;
import lombok.RequiredArgsConstructor;
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

@Component
@RequiredArgsConstructor
public class StartMessageBuilder {

    private static final String CHOOSE_DECK = "/choose_deck";
    private static final String ADD_DECK = "/add_deck";

    private final MessageSource messageSource;

    public List<PartialBotApiMethod<? extends Serializable>> buildStartMenu(UserEntity user, Locale locale) {
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(createInlineKeyboardButton(
                messageSource.getMessage("button.start.choose.deck", null, locale),
                CHOOSE_DECK
        ));
        buttons.add(createInlineKeyboardButton(
                messageSource.getMessage("button.start.add.deck", null, locale),
                ADD_DECK
        ));

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(List.of(buttons));

        SendMessage message = createMessageTemplate(user);
        message.setText(messageSource.getMessage("bot.start.choose.or.add", null, locale));
        message.setReplyMarkup(markup);

        return List.of(message);
    }

    public List<PartialBotApiMethod<? extends Serializable>> buildAddDeckPrompt(UserEntity user, Locale locale) {
        SendMessage message = createMessageTemplate(user);
        message.setText(messageSource.getMessage("bot.add.deck", null, locale));
        return List.of(message);
    }

    public List<PartialBotApiMethod<? extends Serializable>> buildWrongOperationMessage(UserEntity user, Locale locale) {
        SendMessage message = createMessageTemplate(user);
        message.setText(messageSource.getMessage("bot.wrong.operations", null, locale));
        return List.of(message);
    }
}
