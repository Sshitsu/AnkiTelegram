package com.github.shitsu.AnkiTelegram.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "flashcard")
public final class FlashCardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private LocalDate nextReviewedAt;
    private int intervalDays;

    @ManyToOne
    @JoinColumn(name = "deck_id")
    private DeckEntity deck;


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

    public LocalDate getNextReviewedAt() {
        return nextReviewedAt;
    }

    public void setNextReviewedAt(LocalDate nextReviewedAt) {
        this.nextReviewedAt = nextReviewedAt;
    }

    public int getIntervalDays() {
        return intervalDays;
    }

    public void setIntervalDays(int intervalDays) {
        this.intervalDays = intervalDays;
    }

    public DeckEntity getDeck() {
        return deck;
    }

    public void setDeck(DeckEntity deck) {
        this.deck = deck;
    }
}
