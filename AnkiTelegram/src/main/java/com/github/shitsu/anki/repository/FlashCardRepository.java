package com.github.shitsu.anki.repository;

import com.github.shitsu.anki.entity.DeckEntity;
import com.github.shitsu.anki.entity.FlashCardEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface FlashCardRepository extends CrudRepository<FlashCardEntity , Long> {
    List<FlashCardEntity> findByDeck(DeckEntity deck);
    List<FlashCardEntity> findByDeckIdAndNextReviewedAtLessThanEqual(Long deckId, LocalDate date);
}
