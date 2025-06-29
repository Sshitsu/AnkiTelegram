package com.github.shitsu.anki.repository;

import com.github.shitsu.anki.entity.DeckEntity;
import com.github.shitsu.anki.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeckRepository  extends CrudRepository<DeckEntity, Long> {
    List<DeckEntity> findAllByUser(UserEntity user);

}

