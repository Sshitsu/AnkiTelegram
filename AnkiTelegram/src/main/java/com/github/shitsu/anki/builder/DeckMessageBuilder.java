package com.github.shitsu.anki.builder;

import com.github.shitsu.anki.bot.State;
import com.github.shitsu.anki.entity.UserEntity;
import com.github.shitsu.anki.models.Deck;
import com.github.shitsu.anki.sevice.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class DeckMessageBuilder {

    private final UserService userService;
    private final MessageSource messageSource;


    public List<PartialBotApiMethod<? extends Serializable>> buildDeckListMessage(UserEntity user, Locale locale) {

        List<Deck> deckList = userService.getDecks(user);
        if (deckList.isEmpty()) {
            user.setState(State.WAITING_FOR_DECK_NAME);
            userService.save(user);
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

    public List<PartialBotApiMethod<? extends Serializable>> buildDeckOptionsMessage(UserEntity user, Locale locale) {
        user.setState(State.IN_DECK);
        userService.save(user);

        SendMessage message = createMessageTemplate(user);
        message.setText(messageSource.getMessage("bot.deck.options", null, locale));
        message.setReplyMarkup(createDeckOptionsKeyboard());

        return List.of(message);
    }

    public InlineKeyboardMarkup createDeckOptionsKeyboard() {
        return new InlineKeyboardMarkup(List.of(
                List.of(createInlineKeyboardButton("➕ Add Flashcard", "/add_flashcard")),
                List.of(createInlineKeyboardButton("🔁 Start Repetition", "/start_repeat")),
                List.of(createInlineKeyboardButton("❌ Delete Deck", "/delete_deck")),
                List.of(createInlineKeyboardButton("↩️ Back to Decks", "/back_to_decks"))
        ));
    }
}
