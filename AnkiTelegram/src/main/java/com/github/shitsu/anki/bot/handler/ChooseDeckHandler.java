package com.github.shitsu.anki.bot.handler;

import com.github.shitsu.anki.bot.State;
import com.github.shitsu.anki.entity.UserEntity;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.io.Serializable;
import java.util.List;

public class ChooseDeckHandler implements Handler {
    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String message) {
        return List.of();
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return List.of();
    }

    @Override
    public State operatedBotState() {
        return State.CHOOSING_DECK;
    }
}
