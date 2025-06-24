package com.githib.shitsu.AnkiTelegram.bot;

import com.githib.shitsu.AnkiTelegram.sevice.DeckService;
import com.githib.shitsu.AnkiTelegram.sevice.FlashCardService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

@Component
public class AnkiTelegramBot extends TelegramLongPollingBot {
    private final DeckService deckService;
    private final FlashCardService flashCardService;

    @Value("${telegram.bot.token}")
    private String token;

    @Value("${telegram.bot.username}")
    private String botName;

    public AnkiTelegramBot(DeckService deckService, FlashCardService flashCardService) {
        this.deckService = deckService;
        this.flashCardService = flashCardService;
    }
}
