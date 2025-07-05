package com.github.shitsu.anki.bot.handler;

import com.github.shitsu.anki.bot.State;
import com.github.shitsu.anki.builder.DeckMessageBuilder;
import com.github.shitsu.anki.entity.UserContext;
import com.github.shitsu.anki.entity.UserEntity;
import com.github.shitsu.anki.sevice.DeckService;
import com.github.shitsu.anki.sevice.UserContextService;
import com.github.shitsu.anki.sevice.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import static com.github.shitsu.anki.util.TelegramUtil.createInlineKeyboardButton;
import  static com.github.shitsu.anki.util.TelegramUtil.createMessageTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeckOptionsHandler implements Handler{

    private final UserService userService;
    private final DeckService deckService;
    private final MessageSource messageSource;
    private final DeckMessageBuilder deckMessageBuilder;
    private final UserContextService userContextService;

    public static final String ADD_FLASHCARD = "/add_flashcard";
    public static final String START_REPEAT = "/start_repeat";
    public static final String DELETE_DECK = "/delete_deck";
    public static final String BACK_TO_DECKS = "/back_to_decks";


    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String message, Locale locale) {
        return switch (message){
          case ADD_FLASHCARD -> handleAddFlashcard(user, locale);
          case START_REPEAT -> handleStartRepeat(user, locale);
          case DELETE_DECK -> handleDeleteDeck(user, locale);
          case BACK_TO_DECKS -> handleBackToDecks(user, locale);
          default -> showDeckOptions(user, locale);
        };
    }

    private List<PartialBotApiMethod<? extends Serializable>> handleAddFlashcard(UserEntity user,  Locale locale) {
        user.setState(State.ADDING_CARD);
        userService.save(user);

        SendMessage message = createMessageTemplate(user);
        message.setText(messageSource.getMessage("bot.enter.flashcard", null, locale));
        return List.of(message);

    }

    private List<PartialBotApiMethod<? extends Serializable>> handleStartRepeat(UserEntity user, Locale locale) {
        user.setState(State.REPEATING_FLASHCARD);
        userService.save(user);

        SendMessage message = createMessageTemplate(user);
        message.setText(messageSource.getMessage("bot.start.repetition", null, locale));
        return List.of(message);
    }

    private List<PartialBotApiMethod<? extends Serializable>> handleDeleteDeck(UserEntity user, Locale locale) {
        UserContext userContext = userContextService.getContext(user.getChatId());
        Long deckId = userContext.getCurrentDeckId();
        deckService.delete(deckId);
        userContext.setCurrentDeckId(null);
        userContextService.saveContext(user.getChatId(), userContext);
        user.setState(State.CHOOSING_DECK);
        userService.save(user);

        SendMessage message = createMessageTemplate(user);
        message.setText(messageSource.getMessage("bot.deck.deleted", null, locale));

        List<PartialBotApiMethod<? extends Serializable>> deckListMessages = deckMessageBuilder.buildDeckListMessage(user, locale);

        return List.of(message, deckListMessages.get(0));

    }

    private List<PartialBotApiMethod<? extends Serializable>> handleBackToDecks(UserEntity user, Locale locale) {
        UserContext userContext = userContextService.getContext(user.getChatId());
        userContext.setCurrentDeckId(null);
        userContextService.saveContext(user.getChatId(), userContext);

        user.setState(State.CHOOSING_DECK);
        userService.save(user);

        SendMessage message = createMessageTemplate(user);
        message.setText(messageSource.getMessage("bot.back.to.decks", null, locale));
        return List.of(message);
    }

    private List<PartialBotApiMethod<? extends Serializable>> showDeckOptions(UserEntity user, Locale locale) {
        user.setState(State.IN_DECK);
        userService.save(user);

        return deckMessageBuilder.buildDeckOptionsMessage(user, locale);
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return List.of(
                ADD_FLASHCARD,
                START_REPEAT,
                DELETE_DECK,
                BACK_TO_DECKS
        );
    }

    @Override
    public State operatedBotState() {
        return State.IN_DECK;
    }
}
