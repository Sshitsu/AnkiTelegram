package com.github.shitsu.AnkiTelegram.exeception;

public class FlashCardNotFoundException extends RuntimeException {
    public FlashCardNotFoundException(String message) {
        super(message);
    }
}
