package org.example.card.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Optional;

import org.example.card.Repository.CardRepository;
import org.example.card.model.Card;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CardRepositoryTest {
    
    @Autowired
    private CardRepository cardRepository;
    
    @AfterEach
    void tearDown() {
        cardRepository.deleteAll();
    }
    
    @Test
    void findByOibTest() {
        //given
        Card card = new Card(
            "Name",
            "Lastname",
            "12345678901",
            "something"
        );
        cardRepository.save(card);
        //when
        Optional<Card> cardByOib = cardRepository.findByOib("12345678901");
        //then
        assertThat(cardByOib).isPresent();
    }
    
    @Test
    void findByInvalidOibTest() {
        //given
        Card card = new Card(
            "Name",
            "Lastname",
            "12345678901",
            "something"
        );
        cardRepository.save(card);
        //when
        Optional<Card> cardByOib = cardRepository.findByOib("12345678902");
        //then
        assertThat(cardByOib).isNotPresent();
    }
}
