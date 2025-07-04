package com.github.shitsu.anki.bot.handler;

import com.github.shitsu.anki.bot.State;
import com.github.shitsu.anki.builder.DeckMessageBuilder;
import com.github.shitsu.anki.entity.UserEntity;
import com.github.shitsu.anki.sevice.DeckService;
import com.github.shitsu.anki.sevice.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;


import static com.github.shitsu.anki.util.TelegramUtil.createMessageTemplate;


@Component
@RequiredArgsConstructor
@Slf4j
public class ChooseDeckHandler implements Handler {

    private final String CHOSE_DECK = "/chose_deck";

    private final UserService userService;
    private final DeckService deckService;
    private final MessageSource messageSource;
    private final DeckMessageBuilder  deckMessageBuilder;


    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String message, Locale locale) {
        if (message.startsWith(CHOSE_DECK)) {
            return handleDeckSelection(user, message, locale);
        }
        return deckMessageBuilder.buildDeckListMessage(user, locale);
    }

    private List<PartialBotApiMethod<? extends Serializable>> handleDeckSelection(UserEntity user, String message, Locale locale) {
        try {
            Long deckId = Long.parseLong(message.replace("/chose_deck", ""));
            user.setCurrentDeckId(deckId);
            user.setState(State.IN_DECK);
            userService.save(user);

            return deckMessageBuilder.buildDeckOptionsMessage(user, locale);

        } catch (NumberFormatException e) {

            log.error("Deck selection could not be parsed", e);
            SendMessage errorMessage = createMessageTemplate(user);
            errorMessage.setText(messageSource.getMessage("error.invalid.deck.id", null, locale));

            return List.of(errorMessage);
        }
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return List.of(
                CHOSE_DECK
        );
    }

    @Override
    public State operatedBotState() {
        return State.CHOOSING_DECK;
    }
}

