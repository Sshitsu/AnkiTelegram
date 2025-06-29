package com.github.shitsu.anki.bot;

import com.github.shitsu.anki.config.AnkiBotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AnkiTelegramBot extends TelegramLongPollingBot {
    private final UpdateReceiver updateReceiver;
    private final AnkiBotConfig config;

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    public AnkiTelegramBot(UpdateReceiver updateReceiver, AnkiBotConfig config) {
        this.updateReceiver = updateReceiver;
        this.config = config;
        List<BotCommand> listOfCommands = new ArrayList();
        listOfCommands.add(new BotCommand("/start", "start work"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(),  null));
        }catch (TelegramApiException e){
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }


    @Override
    public void onUpdateReceived(Update update) {
       List<PartialBotApiMethod<? extends Serializable>> messagesToSend = updateReceiver.handle(update);
       if(messagesToSend != null && !messagesToSend.isEmpty()){
           messagesToSend.forEach(response -> {
               if(response instanceof SendMessage){
                   executeWithExceptionCheck((SendMessage) response);
               }
           });
       }
    }
    public void executeWithExceptionCheck(SendMessage sendMessage) {
        try{
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error executing command: " + e.getMessage());
        }
    }
}
