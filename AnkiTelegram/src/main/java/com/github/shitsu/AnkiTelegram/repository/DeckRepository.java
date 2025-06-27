package com.github.shitsu.AnkiTelegram.repository;

import com.github.shitsu.AnkiTelegram.entity.DeckEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeckRepository  extends CrudRepository<DeckEntity, Long> {
    DeckEntity findByTitle(String title);
    List<DeckEntity> findByUserId(Long userId);
}

