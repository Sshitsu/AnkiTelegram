package com.github.shitsu.anki.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "deck")
public final class DeckEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String Description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deck")
    private List<FlashCardEntity> flashCardList;

}
