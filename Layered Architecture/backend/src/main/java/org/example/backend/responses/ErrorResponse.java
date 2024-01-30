package org.example.backend.responses;

import java.util.List;

public class ErrorResponse {

    // A user-friendly message that summarizes the error
    private String message;

    // Detailed error messages, typically validation errors
    private List<String> details;

    // Constructors, getters, and setters

    public ErrorResponse(String message, List<String> details) {
        super();
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    // Optionally, override toString for better logging
    @Override
    public String toString() {
        return "ErrorResponse{" +
                "message='" + message + '\'' +
                ", details=" + details +
                '}';
    }
}