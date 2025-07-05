package com.github.shitsu.anki.entity;

import com.github.shitsu.anki.bot.State;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @Column(name = "chat_id", unique = true, nullable = false)
    private Long chatId;

    @Column(nullable = false, unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    private State state;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<DeckEntity> decks;

}
