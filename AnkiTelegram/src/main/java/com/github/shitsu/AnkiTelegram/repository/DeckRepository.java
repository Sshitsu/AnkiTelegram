package com.github.shitsu.AnkiTelegram.repository;

import com.github.shitsu.AnkiTelegram.entity.DeckEntity;
import org.springframework.data.repository.CrudRepository;

public interface DeckRepository  extends CrudRepository<DeckEntity, Long> {
    DeckEntity findByTitle(String title);
}

