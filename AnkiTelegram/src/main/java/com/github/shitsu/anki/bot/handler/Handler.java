package com.github.shitsu.anki.bot.handler;

import com.github.shitsu.anki.bot.State;
import com.github.shitsu.anki.entity.UserEntity;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public interface Handler {
    List<PartialBotApiMethod <? extends Serializable>> handle(UserEntity user, String message, Locale locale);

    List<String> operatedCallBackQuery();

    State operatedBotState();
}
