package com.github.shitsu.anki.bot.handler;

import com.github.shitsu.anki.bot.State;
import com.github.shitsu.anki.builder.DeckMessageBuilder;
import com.github.shitsu.anki.builder.StartMessageBuilder;
import com.github.shitsu.anki.entity.UserEntity;
import com.github.shitsu.anki.sevice.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;


import java.io.Serializable;
import java.util.List;
import java.util.Locale;


@Slf4j
@RequiredArgsConstructor
@Component
public class StartHandler implements Handler {

    private final String CHOOSE_DECK = "/choose_deck";
    private final String ADD_DECK = "/add_deck";
    private final String START = "/start";

    private final MessageSource messageSource;
    private final UserService userService;
    private final DeckMessageBuilder deckMessageBuilder;
    private final StartMessageBuilder startMessageBuilder;


    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String message, Locale locale) {
        log.info("StartHandler.handle() called with message: {}", message);
        user.setState(State.START);
        userService.save(user);

        return switch (message) {
            case CHOOSE_DECK -> choose(user, locale);
            case ADD_DECK -> addDeck(user, locale);
            case START -> startMessageBuilder.buildStartMenu(user, locale);
            default -> startMessageBuilder.buildWrongOperationMessage(user, locale);
        };
    }

    private List<PartialBotApiMethod<? extends Serializable>> choose(UserEntity user, Locale locale) {
        user.setState(State.CHOOSING_DECK);
        userService.save(user);

        return deckMessageBuilder.buildDeckListMessage(user, locale);
    }
    private List<PartialBotApiMethod<? extends Serializable>> addDeck(UserEntity user, Locale locale) {
        user.setState(State.WAITING_FOR_DECK_NAME);
        userService.save(user);
        return startMessageBuilder.buildAddDeckPrompt(user, locale);
    }


    @Override
    public List<String> operatedCallBackQuery() {
        return List.of(CHOOSE_DECK, ADD_DECK);
    }

    @Override
    public State operatedBotState() {
        return State.START;
    }
}
