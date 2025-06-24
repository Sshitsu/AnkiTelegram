package com.githib.shitsu.AnkiTelegram.exeception;

public class DeckNotFoundException extends RuntimeException {
    public DeckNotFoundException(String message) {
        super(message);
    }
}
