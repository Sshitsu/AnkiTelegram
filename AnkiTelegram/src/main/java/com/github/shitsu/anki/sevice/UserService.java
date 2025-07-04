package com.github.shitsu.anki.sevice;

import com.github.shitsu.anki.bot.State;
import com.github.shitsu.anki.entity.DeckEntity;
import com.github.shitsu.anki.entity.UserEntity;
import com.github.shitsu.anki.exeception.UserNotFoundException;
import com.github.shitsu.anki.models.Deck;
import com.github.shitsu.anki.models.User;
import com.github.shitsu.anki.repository.DeckRepository;
import com.github.shitsu.anki.repository.FlashCardRepository;
import com.github.shitsu.anki.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {
    DeckRepository deckRepository;

    FlashCardRepository flashCardRepository;

    UserRepository userRepository;

    public UserService(DeckRepository deckRepository, FlashCardRepository flashCardRepository, UserRepository userRepository) {
        this.deckRepository = deckRepository;
        this.flashCardRepository = flashCardRepository;
        this.userRepository = userRepository;
    }

    public List<Deck> getDecks(UserEntity user) {
        return deckRepository.findAllByUserChatId(user.getChatId())
                .stream()
                .map(Deck::toModel)
                .collect(Collectors.toList());
    }
    public UserEntity findOrCreateUser(Long chatId, String username) {
        return userRepository.findById(chatId)
                .orElseGet(() ->{
                   UserEntity userEntity = new UserEntity();
                    userEntity.setUsername(username);
                    userEntity.setChatId(chatId);
                    userEntity.setState(State.START);
                   return userRepository.save(userEntity);
                });

    }
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

}
