package org.example.card.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table
public class Card {
    @Id
    @SequenceGenerator(
        name = "card_sequence",
        sequenceName = "card_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "card_sequence"
    )
    private Long id;
    @Column(
        nullable = false,
        length = 50
    )
    @NotNull
    private String firstName;
    @Column(
        nullable = false,
        length = 50
    )
    @NotNull
    private String lastName;
    @Column(
        nullable = false,
        unique = true
    )
    @Size(min = 11, max = 11, message = "The string must be exactly 11 characters long")
    @Pattern(regexp = "\\d{11}", message = "The string must contain only numeric characters")
    @NotNull
    private String oib;
    @Column(
        nullable = false,
        length = 50
    )
    @NotNull
    private String status;
    
    public Card() {
    }
    
    public Card(String firstName, String lastName, String oib, String status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.oib = oib;
        this.status = status;
    }
    
    public Card(Long id, String firstName, String lastName, String oib, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.oib = oib;
        this.status = status;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getOib() {
        return oib;
    }
    
    public void setOib(String oib) {
        this.oib = oib;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return " firstName='" + firstName + '\'' + ", lastName='" + lastName + '\''
            + ", oib='" + oib + '\'' + ", status='" + status + '\'' + '}';
    }
}
