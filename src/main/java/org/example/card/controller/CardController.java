package org.example.card.controller;

import org.example.card.errorHandling.CardAlreadyExistsException;
import org.example.card.errorHandling.CardDoesNotExistsException;
import org.example.card.errorHandling.ErrorResponse;
import org.example.card.model.Card;
import org.example.card.model.CardApiResponse;
import org.example.card.model.CreateApiResponse;
import org.example.card.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/v1")
public class CardController {
    
    private final CardService cardService;
    
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }
    
    @GetMapping("/get/{cardOib}")
    public ResponseEntity<CardApiResponse<Card>> getCard(@PathVariable("cardOib") String cardOib) throws CardDoesNotExistsException {
        return new ResponseEntity<>(new CardApiResponse<>(cardService.getCard(cardOib)), HttpStatus.OK);
    }
    
    @PostMapping("/card-request")
    public ResponseEntity<CardApiResponse<CreateApiResponse>> createNewCard(@RequestBody @Valid Card card) throws CardAlreadyExistsException {
        return new ResponseEntity<>(new CardApiResponse<>(new CreateApiResponse(cardService.addNewCard(card))),
            HttpStatus.CREATED);
    }
    
    @DeleteMapping(path = "/delete/{cardOib}")
    public ResponseEntity<Void> deleteCard(@PathVariable("cardOib") String oib) throws CardDoesNotExistsException{
        return cardService.deleteCard(oib);
    }
    
    @ExceptionHandler(value = CardAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleCardAlreadyExistsException(CardAlreadyExistsException e) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), 1, e.getMessage());
    }
    
    @ExceptionHandler(value = CardDoesNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCardDoesNotExistsException(CardDoesNotExistsException e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), 2, e.getMessage());
    }
}
