package com.github.shitsu.AnkiTelegram.repository;

import com.github.shitsu.AnkiTelegram.entity.DeckEntity;
import com.github.shitsu.AnkiTelegram.entity.FlashCardEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface FlashCardRepository extends CrudRepository<FlashCardEntity , Long> {
    List<FlashCardEntity> findByDeck(DeckEntity deck);
    List<FlashCardEntity> findByDeckIdAndNextReviewedAtLessThanEqual(Long deckId, LocalDate date);
}
