package com.github.shitsu.anki.bot.handler;

import com.github.shitsu.anki.bot.State;
import com.github.shitsu.anki.entity.UserEntity;
import com.github.shitsu.anki.sevice.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.github.shitsu.anki.util.TelegramUtil.createMessageTemplate;

@Slf4j
@Component
public class StartHandler implements Handler {
    @Value("${telegram.bot.username}")
    private String botName;

    private final UserService userService;

    public StartHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String message) {
        List<PartialBotApiMethod<? extends Serializable>> messages = new ArrayList<>();

        SendMessage welcomeMessage = createMessageTemplate(user);
        welcomeMessage.setText(String.format(
                        "Hello, I'm *%s*%nI am here to help you memorize new words!", botName
                ));
        messages.add(welcomeMessage);
        if(userService.getDecks(user).isEmpty()) {
            user.setState(State.ADDING_DECK);
            userService.save(user);
            SendMessage addDeckMessage = createMessageTemplate(user);
            addDeckMessage.setText("You don't have any decks, please create a new one!");
            messages.add(addDeckMessage);
        } else{
            user.setState(State.CHOOSING_DECK);
            userService.save(user);
            SendMessage addDeckMessage = createMessageTemplate(user);
            addDeckMessage.setText("Please choose a deck");
            messages.add(addDeckMessage);
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
