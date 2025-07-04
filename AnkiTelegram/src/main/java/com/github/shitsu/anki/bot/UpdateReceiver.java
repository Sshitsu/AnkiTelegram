package com.github.shitsu.anki.bot;

import com.github.shitsu.anki.bot.handler.Handler;
import com.github.shitsu.anki.entity.UserEntity;
import com.github.shitsu.anki.sevice.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Component
@Slf4j
public class UpdateReceiver {

    private final List<Handler> handlers;

    private final UserService userService;

    private final MessageSource source;

    public UpdateReceiver(List<Handler> handlers, UserService userService, MessageSource source) {
        this.handlers = handlers;
        this.userService = userService;
        this.source = source;
    }

    public List<PartialBotApiMethod<? extends Serializable>> handle(Update update) {

        try {
            Locale locale = resolveLocale(update);

            if (isMessageWithText(update)) {
                final Message message = update.getMessage();

                final Long chatId = message.getChatId();

                final UserEntity user = userService.findOrCreateUser(chatId, message.getFrom().getUserName());

                if(message.getText().equals("/start")){
                    user.setState(State.START);
                }

                return getHandlerByState(user.getState()).handle(user, message.getText(), locale);

            } else if (update.hasCallbackQuery()) {

                final CallbackQuery callbackQuery = update.getCallbackQuery();

                final Long chatId = callbackQuery.getFrom().getId();

                final String userName = callbackQuery.getFrom().getUserName();

                final UserEntity user = userService.findOrCreateUser(chatId, userName);

                return getHandlerByCallBackQuery(callbackQuery.getData()).handle(user, callbackQuery.getData(), locale);
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

    private Locale resolveLocale(Update update) {
        String languageCode = null;
        if(isMessageWithText(update)){
            languageCode = update.getMessage().getFrom().getLanguageCode();
        } else if(update.hasCallbackQuery()){
            languageCode = update.getCallbackQuery().getFrom().getLanguageCode();
        }
        return "ru".equalsIgnoreCase(languageCode) ? new Locale("ru") : Locale.ENGLISH;
    }

}
