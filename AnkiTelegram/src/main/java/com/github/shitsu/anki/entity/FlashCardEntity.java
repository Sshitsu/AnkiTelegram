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

    @Column(name = "question")
    private String question;

    @Column(name = "answer")
    private String answer;

    @Column(name = "next_review_date")
    private LocalDate nextReviewedAt;

    @Column(name = "interval_days")
    private int intervalDays;

    @ManyToOne
    @JoinColumn(name = "deck_id")
    private DeckEntity deck;


}
