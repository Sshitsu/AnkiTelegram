package com.github.shitsu.anki.models;

import com.github.shitsu.anki.entity.FlashCardEntity;
import lombok.Data;

@Data
public final class FlashCard {

    private Long id;
    private String answer;
    private String question;

    public static FlashCard toModel(FlashCardEntity flashCardEntity) {
        FlashCard model = new FlashCard();
        model.setId(flashCardEntity.getId());
        model.setQuestion(flashCardEntity.getQuestion());
        model.setAnswer(flashCardEntity.getAnswer());
        return model;
    }



}
