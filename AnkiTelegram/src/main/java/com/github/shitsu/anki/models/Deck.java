package com.github.shitsu.anki.models;

import com.github.shitsu.anki.entity.DeckEntity;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public final class Deck {

    private Long id;

    private String name;

    private String description;

    private List<FlashCard> flashCardList;

    public static Deck toModel(DeckEntity deckEntity) {
        Deck model = new Deck();
        model.setId(deckEntity.getId());
        model.setName(deckEntity.getName());
        model.setDescription(deckEntity.getDescription());
        model.setFlashCardList(
                deckEntity.getFlashCardList()
                        .stream()
                        .map(FlashCard::toModel)
                        .collect(Collectors.toList()));
        return model;
    }


}
