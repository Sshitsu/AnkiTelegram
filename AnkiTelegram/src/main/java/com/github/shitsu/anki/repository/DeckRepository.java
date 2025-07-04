package com.github.shitsu.anki.repository;

import com.github.shitsu.anki.entity.DeckEntity;
import com.github.shitsu.anki.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeckRepository  extends CrudRepository<DeckEntity, Long> {

    @Query("SELECT d FROM DeckEntity d LEFT JOIN FETCH d.flashCardList WHERE d.user.chatId = :chatId")
    List<DeckEntity> findAllByUserChatId(@Param("chatId") Long chatId);

    boolean existsByUserChatId(@Param("chatId") Long chatId);
}

