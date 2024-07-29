package org.example.card.service;

import java.util.Optional;

import org.example.card.Repository.CardRepository;
import org.example.card.errorHandling.CardAlreadyExistsException;
import org.example.card.errorHandling.CardDoesNotExistsException;
import org.example.card.model.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    private final CardRepository cardRepository;
    
    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }
    
    public Card getCard(String cardOib) throws CardDoesNotExistsException {
        Optional<Card> cardByOib = cardRepository.findByOib(cardOib);
        if (!cardByOib.isPresent()) {
            throw new CardDoesNotExistsException("Card with OIB: " + cardOib + " does not exist");
        }
        return cardByOib.orElse(null);
    }
    
    public String addNewCard(Card card) throws CardAlreadyExistsException{
        Optional<Card> cardByOib =  cardRepository.findByOib(card.getOib());
        if(cardByOib.isPresent()) {
            throw new CardAlreadyExistsException("Card with OIB: " + card.getOib() + " already exists");
        }
        cardRepository.save(card);
        return "New card request successfully created";
    }
    
    public ResponseEntity<Void> deleteCard(String cardOib) throws CardDoesNotExistsException {
        Optional<Card> cardByOib = cardRepository.findByOib(cardOib);
        if (!cardByOib.isPresent()) {
            throw new CardDoesNotExistsException("Card with OIB: " + cardOib + " does not exist");
        }
        cardRepository.delete(cardByOib.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
