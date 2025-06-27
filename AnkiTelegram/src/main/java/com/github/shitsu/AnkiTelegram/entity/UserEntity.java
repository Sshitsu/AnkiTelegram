package com.github.shitsu.AnkiTelegram.entity;

import com.github.shitsu.AnkiTelegram.bot.State;
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

    private State state;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<DeckEntity> decks;


}
