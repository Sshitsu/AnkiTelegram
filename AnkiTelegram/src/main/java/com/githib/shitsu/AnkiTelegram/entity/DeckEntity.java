package com.githib.shitsu.AnkiTelegram.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "deck")
public final class DeckEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String Description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "decks")
    private List<FlashCardEntity> flashCardList;

}
