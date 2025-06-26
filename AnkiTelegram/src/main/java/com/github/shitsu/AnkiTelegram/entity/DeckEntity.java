package com.github.shitsu.AnkiTelegram.entity;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deck")
    @JoinColumn(name = "user_id")
    private List<FlashCardEntity> flashCardList;

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

    public List<FlashCardEntity> getFlashCardList() {
        return flashCardList;
    }

    public void setFlashCardList(List<FlashCardEntity> flashCardList) {
        this.flashCardList = flashCardList;
    }
}
