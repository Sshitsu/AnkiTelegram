package com.github.shitsu.anki.sevice;

import com.github.shitsu.anki.entity.DeckEntity;
import com.github.shitsu.anki.entity.UserEntity;
import com.github.shitsu.anki.exeception.DeckNotFoundException;
import com.github.shitsu.anki.models.Deck;
import com.github.shitsu.anki.repository.DeckRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DeckService {

    private final DeckRepository deckRepository;

    public DeckService(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }


    public DeckEntity addDeckToUser(UserEntity user, String deckName) {
        DeckEntity deckEntity = new DeckEntity();
        deckEntity.setUser(user);
        deckEntity.setName(deckName);
        deckRepository.save(deckEntity);
        return deckEntity;
    }

    public  boolean isAnyDeckExistByUserChatId(Long chatId) {
        return deckRepository.existsByUserChatId(chatId);
    }

    public Long delete(Long id){
        deckRepository.deleteById(id);
        return id;
    }


}
