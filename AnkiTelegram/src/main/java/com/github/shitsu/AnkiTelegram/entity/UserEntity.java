package com.github.shitsu.AnkiTelegram.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    private Long chatId;

    private String firstName;

    private String lastName;

    private String username;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<DeckEntity> decks;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long userId) {
        this.chatId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
