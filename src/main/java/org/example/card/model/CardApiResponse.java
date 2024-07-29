package org.example.card.model;

public class CardApiResponse<T> {
    
    private T response;
    
    public CardApiResponse(T response) {
        this.response = response;
    }
    
    public T getResponse() {
        return response;
    }
    
    public void setResponse(T response) {
        this.response = response;
    }
}
