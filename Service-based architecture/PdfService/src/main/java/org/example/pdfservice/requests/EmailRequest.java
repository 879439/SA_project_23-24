package org.example.pdfservice.requests;



import org.example.pdfservice.models.Passenger;

import java.util.List;

public class EmailRequest {
    private List<Passenger> passengers;
    private String subject;
    private String text;
    private byte[] attachment;

    public EmailRequest(List<Passenger> passengers, String subject, String text, byte[] attachment) {
        this.passengers = passengers;
        this.subject = subject;
        this.text = text;
        this.attachment = attachment;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }
}
