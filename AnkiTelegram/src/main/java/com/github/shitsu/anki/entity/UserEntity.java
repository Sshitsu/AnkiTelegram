package com.github.shitsu.anki.entity;

import com.github.shitsu.anki.bot.State;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    private Long chatId;

    private String username;

    @Enumerated(EnumType.STRING)
    private State state;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<DeckEntity> decks;

    public UserEntity(Long chatId, String username, State state) {
        this.chatId = chatId;
        this.username = username;
        this.state = state;
    }

}
