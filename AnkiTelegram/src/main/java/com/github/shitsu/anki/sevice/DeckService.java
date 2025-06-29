package com.github.shitsu.anki.sevice;

import com.github.shitsu.anki.entity.DeckEntity;
import com.github.shitsu.anki.entity.UserEntity;
import com.github.shitsu.anki.exeception.DeckNotFoundException;
import com.github.shitsu.anki.models.Deck;
import com.github.shitsu.anki.repository.DeckRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public final class DeckService {

    private final DeckRepository deckRepository;

    public DeckService(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    public DeckEntity addDeck(DeckEntity deck){
        return deckRepository.save(deck);
    }

    public List<Deck> getAllDecks(UserEntity user) {
        List<Deck> decks = new ArrayList<>();
        for (DeckEntity deckEntity : deckRepository.findAllByUser(user)) {
            decks.add(Deck.toModel(deckEntity));
        }
        return decks;
    }

    public Deck getDeck(Long id) throws DeckNotFoundException {
        return Deck.toModel(Objects.requireNonNull(deckRepository
                .findById(id)
                .orElseThrow(() -> new DeckNotFoundException("Deck Not Found!"))));
    }

    public Long delete(Long id){
        deckRepository.deleteById(id);
        return id;
    }


}
