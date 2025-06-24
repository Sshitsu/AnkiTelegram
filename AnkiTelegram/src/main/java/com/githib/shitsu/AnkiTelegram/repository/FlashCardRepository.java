package com.githib.shitsu.AnkiTelegram.repository;

import com.githib.shitsu.AnkiTelegram.entity.DeckEntity;
import com.githib.shitsu.AnkiTelegram.entity.FlashCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface FlashCardRepository extends CrudRepository<FlashCardEntity , Long> {
    List<FlashCardEntity> findByDeck(DeckEntity deck);
    List<FlashCardEntity> findByDeckIdAndNextReviewedAtLessThanEqual(Long deckId, LocalDate date);
}
