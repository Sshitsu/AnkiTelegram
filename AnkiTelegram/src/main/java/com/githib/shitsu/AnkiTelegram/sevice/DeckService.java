package com.githib.shitsu.AnkiTelegram.sevice;

import com.githib.shitsu.AnkiTelegram.entity.DeckEntity;
import com.githib.shitsu.AnkiTelegram.exeception.DeckNotFoundException;
import com.githib.shitsu.AnkiTelegram.models.Deck;
import com.githib.shitsu.AnkiTelegram.models.FlashCard;
import com.githib.shitsu.AnkiTelegram.repository.DeckRepository;
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

    public List<Deck> getAllDecks() {
        List<Deck> decks = new ArrayList<>();
        for (DeckEntity deckEntity : deckRepository.findAll()) {
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
