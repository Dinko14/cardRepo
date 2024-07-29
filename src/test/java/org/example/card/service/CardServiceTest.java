package org.example.card.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.example.card.Repository.CardRepository;
import org.example.card.errorHandling.CardAlreadyExistsException;
import org.example.card.errorHandling.CardDoesNotExistsException;
import org.example.card.model.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {
    
    private CardService cardService;
    @Mock
    private CardRepository cardRepository;
    
    @BeforeEach
    void setUp() {
        cardService = new CardService(cardRepository);
    }
    
    @Test
    void testAddNewCard() throws CardAlreadyExistsException {
        Card card = new Card("name",
                         "lastname",
                           "12345678910",
                         "Status");
        cardService.addNewCard(card);
        
        ArgumentCaptor<Card> cardCaptor = ArgumentCaptor.forClass(Card.class);
        verify(cardRepository).save(cardCaptor.capture());

        Card capturedCard = cardCaptor.getValue();

        assertThat(capturedCard).isEqualTo(card);
    }
    
    @Test
    void testAddNewCardThrowWhenAlreadyExists() throws CardAlreadyExistsException {
        Card card = new Card("name",
            "lastname",
            "12345678910",
            "Status");

        given(cardRepository.findByOib(card.getOib())).willReturn(Optional.of(new Card()));
        assertThatThrownBy(() -> cardService.addNewCard(card))
            .isInstanceOf(CardAlreadyExistsException.class)
            .hasMessageContaining("Card with OIB: " + card.getOib() + " already exists");
        verify(cardRepository, never()).save(any());
    }
    
    @Test
    void testGetCardByOibThrowDoesNotExists() throws CardDoesNotExistsException {
        String cardOib = "12345678910";
        given(cardRepository.findByOib(cardOib)).willReturn(Optional.empty());
        assertThatThrownBy(() -> cardService.getCard(cardOib))
            .isInstanceOf(CardDoesNotExistsException.class)
            .hasMessageContaining("Card with OIB: " + cardOib + " does not exist");
        
    }
    
    @Test
    void testGetCardByOib() throws CardDoesNotExistsException {
        Card expectedCard = new Card(
            "name",
            "lastname",
            "12345678910",
            "status"
        );
        
        given(cardRepository.findByOib(expectedCard.getOib())).willReturn(Optional.of(expectedCard));
        
        Card actualCard = cardService.getCard(expectedCard.getOib());
        
        assertThat(actualCard).isEqualTo(expectedCard);
    }
    
    @Test
    void testDeleteCardThrowDoesNotExists() throws CardDoesNotExistsException {
        String cardOib = "12345678910";
        given(cardRepository.findByOib(cardOib)).willReturn(Optional.empty());
        assertThatThrownBy(() -> cardService.deleteCard(cardOib))
            .isInstanceOf(CardDoesNotExistsException.class)
            .hasMessageContaining("Card with OIB: " + cardOib + " does not exist");
        
    }
    
    @Test
    void testDeleteCard() throws CardDoesNotExistsException {
        Card card = new Card(
            "name",
            "lastname",
            "12345678910",
            "status"
        );
        given(cardRepository.findByOib(card.getOib())).willReturn(Optional.of(card));
        
        ResponseEntity<Void> response = cardService.deleteCard(card.getOib());
        
        verify(cardRepository, times(1)).delete(card);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }
}
