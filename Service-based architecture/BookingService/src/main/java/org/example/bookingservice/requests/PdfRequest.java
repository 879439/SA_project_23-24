package org.example.bookingservice.requests;


import org.example.bookingservice.models.Booking;
import org.example.bookingservice.models.TicketInfo;

public class PdfRequest {
    private Booking booking;
    private TicketInfo ticketInfo;

    public PdfRequest(Booking booking, TicketInfo ticketInfo) {
        this.booking = booking;
        this.ticketInfo = ticketInfo;
    }

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
