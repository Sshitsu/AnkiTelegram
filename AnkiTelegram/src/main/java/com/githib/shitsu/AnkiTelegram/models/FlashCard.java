package com.githib.shitsu.AnkiTelegram.models;

import com.githib.shitsu.AnkiTelegram.entity.DeckEntity;
import com.githib.shitsu.AnkiTelegram.entity.FlashCardEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

public final class FlashCard {

    private Long id;
    private String title;
    private String description;

    public static FlashCard toModel(FlashCardEntity flashCardEntity) {
        FlashCard model = new FlashCard();
        model.setId(flashCardEntity.getId());
        model.setTitle(flashCardEntity.getTitle());
        model.setDescription(flashCardEntity.getDescription());
        return model;
    }

    public FlashCard() {}

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
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
