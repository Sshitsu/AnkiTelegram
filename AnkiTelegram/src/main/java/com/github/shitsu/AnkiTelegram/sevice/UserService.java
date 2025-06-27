package com.github.shitsu.AnkiTelegram.sevice;

import com.github.shitsu.AnkiTelegram.entity.DeckEntity;
import com.github.shitsu.AnkiTelegram.entity.UserEntity;
import com.github.shitsu.AnkiTelegram.exeception.UserNotFoundException;
import com.github.shitsu.AnkiTelegram.models.Deck;
import com.github.shitsu.AnkiTelegram.models.User;
import com.github.shitsu.AnkiTelegram.repository.DeckRepository;
import com.github.shitsu.AnkiTelegram.repository.FlashCardRepository;
import com.github.shitsu.AnkiTelegram.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    DeckRepository deckRepository;

    FlashCardRepository flashCardRepository;

    UserRepository userRepository;

    public UserService(DeckRepository deckRepository, FlashCardRepository flashCardRepository) {
        this.deckRepository = deckRepository;
        this.flashCardRepository = flashCardRepository;
    }
    public List<Deck> getDecks(Long userId ) {
        return deckRepository
                .findByUserId(userId)
                .stream()
                .map(Deck::toModel)
                .collect(Collectors.toList());
    }
    public User getUser(Long userId) {
        return User.toModel(userRepository
                .findById(userId)
                .orElseThrow(() ->  new UserNotFoundException("User not found!")));
    }
    public UserEntity addUser(UserEntity user) {
        return userRepository.save(user);
    }

}
