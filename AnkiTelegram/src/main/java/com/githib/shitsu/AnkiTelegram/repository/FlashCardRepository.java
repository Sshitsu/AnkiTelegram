package com.githib.shitsu.AnkiTelegram.repository;

import com.githib.shitsu.AnkiTelegram.entity.DeckEntity;
import com.githib.shitsu.AnkiTelegram.entity.FlashCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlashCardRepository extends JpaRepository<FlashCardEntity, Long> {
    List<FlashCardEntity> findByDeck(DeckEntity deck);
}
