package com.github.shitsu.anki.exeception;

public class DeckNotFoundException extends RuntimeException {
    public DeckNotFoundException(String message) {
        super(message);
    }
}
