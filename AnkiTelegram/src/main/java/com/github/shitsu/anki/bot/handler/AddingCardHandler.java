package com.github.shitsu.anki.bot.handler;

import com.github.shitsu.anki.bot.State;
import com.github.shitsu.anki.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class AddingCardHandler  implements Handler {
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
