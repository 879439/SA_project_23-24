package org.example.flightservice.responses;

public class MessageResponse {
    private String message;
    private MessageResponse(){

    }
    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
