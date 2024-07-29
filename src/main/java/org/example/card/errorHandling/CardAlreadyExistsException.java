
package org.example.card.errorHandling;

import lombok.Data;

@Data
public class CardAlreadyExistsException extends Exception {
    private static final long serialVersionUID = 1L;
    private String message;
    
    public CardAlreadyExistsException() {}
    
    public CardAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }
}
