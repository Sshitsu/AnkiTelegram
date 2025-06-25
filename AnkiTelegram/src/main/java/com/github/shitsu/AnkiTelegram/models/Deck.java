package com.github.shitsu.AnkiTelegram.models;

import com.github.shitsu.AnkiTelegram.entity.DeckEntity;

import java.util.List;
import java.util.stream.Collectors;

public final class Deck {

    private Long id;

    private String title;

    private String Description;

    private List<FlashCard> flashCardList;

    public static Deck toModel(DeckEntity deckEntity) {
        Deck model = new Deck();
        model.setId(deckEntity.getId());
        model.setTitle(deckEntity.getTitle());
        model.setDescription(deckEntity.getDescription());
        model.setFlashCardList(
                deckEntity.getFlashCardList()
                        .stream()
                        .map(FlashCard::toModel)
                        .collect(Collectors.toList()));
        return model;
    }

    public Deck() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public List<FlashCard> getFlashCardList() {
        return flashCardList;
    }

    public void setFlashCardList(List<FlashCard> flashCardList) {
        this.flashCardList = flashCardList;
    }
}
