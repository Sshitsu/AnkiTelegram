package com.github.shitsu.anki.builder;

import com.github.shitsu.anki.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.Locale;

import static com.github.shitsu.anki.util.TelegramUtil.createInlineKeyboardButton;
import static com.github.shitsu.anki.util.TelegramUtil.createMessageTemplate;

@Component
@RequiredArgsConstructor
public class AddCardMessageBuilder {

    private final MessageSource messageSource;

    public SendMessage buildConfirmCardMessage(UserEntity user, String front, String back, Locale locale) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(List.of(
                List.of(
                        createInlineKeyboardButton("✅ Save", "/save_card"),
                        createInlineKeyboardButton("♻️ Edit", "/edit_card"),
                        createInlineKeyboardButton("↩️ Cancel", "/cancel_card")
                )
        ));

        SendMessage message = createMessageTemplate(user);
        message.setText(messageSource.getMessage("bot.card.preview", new Object[]{front, back}, locale));
        message.setReplyMarkup(markup);
        return message;
    }
}
