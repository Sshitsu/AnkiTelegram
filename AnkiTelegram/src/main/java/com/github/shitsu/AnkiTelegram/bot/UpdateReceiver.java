package com.github.shitsu.AnkiTelegram.bot;

import com.github.shitsu.AnkiTelegram.bot.handler.Handler;
import com.github.shitsu.AnkiTelegram.entity.UserEntity;
import com.github.shitsu.AnkiTelegram.repository.FlashCardRepository;
import com.github.shitsu.AnkiTelegram.sevice.DeckService;
import com.github.shitsu.AnkiTelegram.sevice.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Component
public class UpdateReceiver {
    private final List<Handler> handlers;

    private final UserService userService;


    public UpdateReceiver(List<Handler> handlers, UserService userService) {
        this.handlers = handlers;
        this.userService = userService;
    }

    public List<PartialBotApiMethod<? extends Serializable>> handle(Update update) {
        try {
            if (isMessageWithText(update)) {
                final Message message = update.getMessage();

                final Long chatId = message.getChatId();

                final UserEntity user = userService.addUser(chatId, message.getFrom().getUserName());

                return getHandlerByState(user.getState()).handle(user, message.getText());
            } else if (update.hasCallbackQuery()) {
                final CallbackQuery callbackQuery = update.getCallbackQuery();
                final Long chatId = callbackQuery.getFrom().getId();
                final UserEntity user = userService.addUser(chatId, callbackQuery.getFrom().getUserName());
                return getHandlerByCallBackQuery(callbackQuery.getData()).handle(user, callbackQuery.getData());
            }
            throw new UnsupportedOperationException("Not supported yet.");
        }catch (UnsupportedOperationException e){
            return Collections.emptyList();
        }
    }

    private Handler getHandlerByState(State state) {
        return handlers.stream()
                .filter(h -> h.operatedBotState() != null)
                .filter(h -> h.operatedBotState().equals(state))
                .findFirst()
                .orElseThrow(UnsupportedOperationException::new);
    }
    private Handler getHandlerByCallBackQuery(String query) {
        return handlers.stream()
                .filter(h -> h.operatedCallBackQuery().stream()
                        .anyMatch(query::startsWith))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }

    private boolean isMessageWithText(Update update) {
        return !update.hasCallbackQuery() && update.hasMessage() && update.getMessage().hasText();
    }

}
