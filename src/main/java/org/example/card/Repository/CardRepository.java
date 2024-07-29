package org.example.card.Repository;

import java.util.Optional;

import org.example.card.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    
    @Query("SELECT s FROM Card s WHERE s.oib = ?1")
    Optional<Card> findByOib(String oib);
}
