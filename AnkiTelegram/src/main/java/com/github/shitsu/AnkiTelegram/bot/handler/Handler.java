package com.github.shitsu.AnkiTelegram.bot.handler;

import com.github.shitsu.AnkiTelegram.bot.State;
import com.github.shitsu.AnkiTelegram.entity.UserEntity;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.io.Serializable;
import java.util.List;

public interface Handler {
    List<PartialBotApiMethod <? extends Serializable>> handle(UserEntity user, String message);

    List<String> operatedCallBackQuery();

    State operatedBotState();
}
