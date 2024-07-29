package org.example.card.errorHandling;

import lombok.Data;

@Data
public class CardDoesNotExistsException extends Exception{
    private static final long serialVersionUID = 2L;
    private String message;
    
    public CardDoesNotExistsException() {}
    
    public CardDoesNotExistsException(String message) {
        super(message);
        this.message = message;
    }
}
