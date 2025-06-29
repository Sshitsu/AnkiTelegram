package com.github.shitsu.anki.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "flashcard")
public final class FlashCardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String answer;

    private LocalDate nextReviewedAt;

    private int intervalDays;

    @ManyToOne
    @JoinColumn(name = "deck_id")
    private DeckEntity deck;


}
