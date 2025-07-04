package com.github.shitsu.anki.sevice;

import com.github.shitsu.anki.entity.DeckEntity;
import com.github.shitsu.anki.entity.FlashCardEntity;
import com.github.shitsu.anki.exeception.FlashCardNotFoundException;
import com.github.shitsu.anki.models.FlashCard;
import com.github.shitsu.anki.repository.DeckRepository;
import com.github.shitsu.anki.repository.FlashCardRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public final class FlashCardService {

    private final DeckRepository deckRepository;

    private final FlashCardRepository flashCardRepository;

    public FlashCardService(DeckRepository deckRepository, FlashCardRepository flashCardRepository) {
        this.deckRepository = deckRepository;
        this.flashCardRepository = flashCardRepository;
    }

    public FlashCard getCardById(Long id){
        return FlashCard.toModel(flashCardRepository
                .findById(id)
                .orElseThrow(() ->  new FlashCardNotFoundException("Flashcard not Found!")));
    }

    public FlashCard addCard(Long deckId, FlashCardEntity flashCardEntity) {
        DeckEntity deckEntity = deckRepository.findById(deckId).orElse(null);
        flashCardEntity.setDeck(deckEntity);
        return FlashCard.toModel(flashCardRepository.save(flashCardEntity));
    }

//    public void reviewCard(Long flashCardId, boolean success ){
//        FlashCardEntity flashCardEntity = flashCardRepository
//                .findById(flashCardId)
//                .orElseThrow(() -> new FlashCardNotFoundException("Flashcard not Found!"));
//        if(success){
//            int newInterval = flashCardEntity.getIntervalDays() * 2;
//            flashCardEntity.setIntervalDays(newInterval);
//            flashCardEntity.setNextReviewedAt(LocalDate.now().plusDays(newInterval));
//            flashCardRepository.save(flashCardEntity);
//        } else {
//            flashCardEntity.setIntervalDays(1);
//            flashCardEntity.setNextReviewedAt(LocalDate.now().plusDays(1));
//        }
//    }

    public List<FlashCard> getDueCards(Long deckId){
        return flashCardRepository.findByDeckIdAndNextReviewedAtLessThanEqual(deckId, LocalDate.now())
                .stream()
                .map(FlashCard::toModel)
                .collect(Collectors.toList());
    }


}
