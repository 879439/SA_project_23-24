package org.example.pdfservice.requests;

import org.example.pdfservice.models.Booking;
import org.example.pdfservice.models.TicketInfo;

public class PdfRequest {
    private Booking booking;
    private TicketInfo ticketInfo;

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public TicketInfo getTicketInfo() {
        return ticketInfo;
    }

    public void setTicketInfo(TicketInfo ticketInfo) {
        this.ticketInfo = ticketInfo;
    }
}
