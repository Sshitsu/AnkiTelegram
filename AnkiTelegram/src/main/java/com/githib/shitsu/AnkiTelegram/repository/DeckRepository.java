package com.githib.shitsu.AnkiTelegram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.githib.shitsu.AnkiTelegram.entity.DeckEntity;
import org.springframework.data.repository.CrudRepository;

public interface DeckRepository  extends CrudRepository<DeckEntity, Long> {
    DeckEntity findByTitle(String title);
}

