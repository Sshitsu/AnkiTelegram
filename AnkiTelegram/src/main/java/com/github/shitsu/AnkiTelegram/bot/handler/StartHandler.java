package com.github.shitsu.AnkiTelegram.bot.handler;

import com.github.shitsu.AnkiTelegram.bot.State;
import com.github.shitsu.AnkiTelegram.entity.UserEntity;
import com.github.shitsu.AnkiTelegram.sevice.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static com.github.shitsu.AnkiTelegram.util.TelegramUtil.createMessageTemplate;

public class StartHandler implements Handler {
    @Value("${telegram.bot.name}")
    private String botName;

    private final UserService userService;

    public StartHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String message) {
        SendMessage welcomeMessage = createMessageTemplate(user);
        welcomeMessage.setText(String.format(
                        "Hello, I'm *%s*%nI ams here to help you memorize new words!", botName
                ));
        SendMessage registrationMessage  = createMessageTemplate(user);
        registrationMessage.setText("In order to start write your name");
        user.setState(State.ENTER_NAME);
        userService.addUser(user);

        return List.of(welcomeMessage, registrationMessage);
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
