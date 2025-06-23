package com.githib.shitsu.AnkiTelegram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.githib.shitsu.AnkiTelegram.entity.DeckEntity;
public interface DeckRepository  extends JpaRepository<DeckEntity, Long> {
    DeckEntity findByTitle(String title);
}

