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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_chat_id",
            referencedColumnName = "chat_id",
            foreignKey = @ForeignKey(name = "fk_deck_user_chat"),
            nullable = false
    )
    private UserEntity user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deck")
    private List<FlashCardEntity> flashCardList;

}
