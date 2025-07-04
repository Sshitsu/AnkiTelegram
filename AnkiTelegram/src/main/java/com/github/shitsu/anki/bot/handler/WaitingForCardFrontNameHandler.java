package com.github.shitsu.anki.bot.handler;

import com.github.shitsu.anki.bot.State;
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

@Component
@RequiredArgsConstructor
@Slf4j
public class WaitingForCardFrontNameHandler implements Handler {

    private final UserService userService;
    private final MessageSource messageSource;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String message, Locale locale) {
        return List.of();
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return List.of();
    }

    @Override
    public State operatedBotState() {
        return null;
    }

}
