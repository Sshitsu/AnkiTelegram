package com.github.shitsu.anki.builder;

import com.github.shitsu.anki.entity.UserEntity;
import com.github.shitsu.anki.models.Deck;
import com.github.shitsu.anki.sevice.UserService;
import lombok.NoArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.github.shitsu.anki.util.TelegramUtil.createInlineKeyboardButton;
import static com.github.shitsu.anki.util.TelegramUtil.createMessageTemplate;

@Component
public class DeckMessageBuilder {

    private final UserService userService;
    private final MessageSource messageSource;

    public DeckMessageBuilder(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }
    public List<PartialBotApiMethod<? extends Serializable>> buildDeckListMessage(UserEntity user, Locale locale) {

        List<Deck> deckList = userService.getDecks(user);
        if (deckList.isEmpty()) {
            SendMessage noDecksMessage = createMessageTemplate(user);
            noDecksMessage.setText(messageSource.getMessage("bot.no.decks", null, locale));
            return List.of(noDecksMessage);
        }

        List<List<InlineKeyboardButton>> rows = deckList.stream()
                .map(deck -> List.of(
                        createInlineKeyboardButton(deck.getName(), "/chose_deck" + deck.getId())
                ))
                .collect(Collectors.toList());

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);

        SendMessage chooseDeckMessage = createMessageTemplate(user);
        chooseDeckMessage.setText(messageSource.getMessage("bot.choose.deck", null, locale));
        chooseDeckMessage.setReplyMarkup(markup);

        return List.of(chooseDeckMessage);
    }
}
