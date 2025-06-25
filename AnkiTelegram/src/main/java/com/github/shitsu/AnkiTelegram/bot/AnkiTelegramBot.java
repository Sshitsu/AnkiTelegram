package com.github.shitsu.AnkiTelegram.bot;

import com.github.shitsu.AnkiTelegram.config.AnkiBotConfig;
import com.github.shitsu.AnkiTelegram.sevice.DeckService;
import com.github.shitsu.AnkiTelegram.sevice.FlashCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AnkiTelegramBot extends TelegramLongPollingBot {
    private final DeckService deckService;
    private final FlashCardService flashCardService;

    private final AnkiBotConfig config;

    public AnkiTelegramBot(DeckService deckService, FlashCardService flashCardService, AnkiBotConfig config) {
        this.deckService = deckService;
        this.flashCardService = flashCardService;
        this.config = config;
        List<BotCommand> listOfCommands = new ArrayList();
        listOfCommands.add(new BotCommand("/start", "start work"));
        listOfCommands.add(new BotCommand("/stop", "stops the bot"));
        listOfCommands.add(new BotCommand("/help", "how to use this bot"));
        listOfCommands.add(new BotCommand("/listdecks", "get decks"));
        listOfCommands.add(new BotCommand("/deletedeck", "delete a deck by Id"));
        listOfCommands.add(new BotCommand("/adddeck", "create a new deck"));
        listOfCommands.add(new BotCommand("/addcard", "create a new flash card"));
        listOfCommands.add(new BotCommand("/review", "start review card from deck, which has  been chosen"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(),  null));
        }catch (TelegramApiException e){
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("Received update: {}", update);
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText =  update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                    sendMsg(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default : sendMsg(chatId, "Sorry");
            }
        }
    }

    private void sendMsg(Long chatId, String text) {
        String answer = "Hi, " + text + ", nice to meet you!";
        try {
            SendMessage message = new SendMessage();
            message.setChatId(chatId.toString());
            message.setText(answer);
            execute(message);
            log.info("Sent message to {} (text: {})", chatId, text);
        } catch (TelegramApiException e) {
            log.error("Failed to send message to {}: {}", chatId, e.getMessage(), e);

        }
    }
}
