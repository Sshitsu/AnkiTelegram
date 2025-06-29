package com.github.shitsu.anki.exeception;

public class FlashCardNotFoundException extends RuntimeException {
    public FlashCardNotFoundException(String message) {
        super(message);
    }
}
