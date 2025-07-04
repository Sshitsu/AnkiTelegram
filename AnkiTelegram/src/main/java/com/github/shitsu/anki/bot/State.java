package com.github.shitsu.anki.bot;

public enum State {
    NONE,
    START,
    WAITING_FOR_DECK_NAME,
    CHOOSING_DECK,
    IN_DECK,
    ADDING_CARD,
    ADDING_DECK,
    REPEATING_FLASHCARD,
}
