package com.github.shitsu.anki.bot.handler;

import com.github.shitsu.anki.bot.State;
import com.github.shitsu.anki.entity.UserEntity;
import com.github.shitsu.anki.sevice.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static com.github.shitsu.anki.util.TelegramUtil.createMessageTemplate;

@Slf4j
@Component
public class StartHandler implements Handler {
    @Value("${telegram.bot.username}")
    private String botName;

    private final MessageSource messageSource;

    private final UserService userService;

    public StartHandler(MessageSource messageSource, UserService userService) {
        this.messageSource = messageSource;
        this.userService = userService;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String message, Locale locale) {
        List<PartialBotApiMethod<? extends Serializable>> messages = new ArrayList<>();
        SendMessage welcomeMessage = createMessageTemplate(user);
        welcomeMessage.setText( messageSource.getMessage(
                "bot.welcome", new Object[]{botName}, locale
        ));
        messages.add(welcomeMessage);


        if(userService.getDecks(user).isEmpty()) {
            user.setState(State.ADDING_DECK);
            userService.save(user);
            SendMessage addDeckMessage = createMessageTemplate(user);
            addDeckMessage.setText(
                    messageSource.getMessage(
                            "bot.no.decks", new Object[]{}, locale
                    ));
            messages.add(addDeckMessage);
        } else{
            user.setState(State.CHOOSING_DECK);
            userService.save(user);
            SendMessage choseDeck = createMessageTemplate(user);
            choseDeck.setText(
                    messageSource.getMessage(
                            "bot.choose.deck",new Object[]{}, locale
                    ));
            messages.add(choseDeck);
        }


        return messages;
    }


    @Override
    public List<String> operatedCallBackQuery() {
        return Collections.emptyList();
    }

    @Override
    public State operatedBotState() {
        return State.START;
    }
}
