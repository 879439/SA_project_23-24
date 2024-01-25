import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BookingService } from '../booking.service';
import { Flight } from '../flight.model';
import html2canvas from 'html2canvas';
import domtoimage from 'dom-to-image';

@Component({
  selector: 'app-view-ticket',
  templateUrl: './view-ticket.component.html',
  styleUrls: ['./view-ticket.component.scss']
})
export class ViewTicketComponent implements OnInit {
  bookingDetails: any;
  selectedFlight: Flight | null = null;

  @ViewChild('ticketContainer', { static: true }) ticketContainer!: ElementRef;

  constructor(private route: ActivatedRoute, private bookingService: BookingService) { }

  ngOnInit() {
    // Get the booking details from query parameters
    this.route.queryParams.subscribe(params => {
      const { passengerName, passengerEmail, passengerPhone, seatSelection, flightId } = params;
      const pnrNumber = this.generatePNRNumber();
      // Retrieve the selected flight based on flightId (you can fetch it from the FlightService or use the saved bookings)
      const bookings = this.bookingService.getBookings();
      const selectedBooking = bookings.find(booking => booking.flight.id === flightId);

      // Set the booking details and selected flight
      this.bookingDetails = {
        passengerName,
        passengerEmail,
        passengerPhone,
        seatSelection,
        flightId,
        pnrNumber,
        bookingDate: new Date().toLocaleDateString()
      };
      this.selectedFlight = selectedBooking ? selectedBooking.flight : null;
    });
  }
  generatePNRNumber(): string {
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    const length = 10;
    let pnr = '';
  
    for (let i = 0; i < length; i++) {
      const randomIndex = Math.floor(Math.random() * characters.length);
      pnr += characters.charAt(randomIndex);
    }
  
    return pnr;
  }

  getPriceFormatted(price: number | undefined): string {
    if (price === undefined) {
      return 'N/A';
    }
  
    return price.toLocaleString('it-IT', { style: 'currency', currency: 'EUR' });
  }

  downloadTicket() {
    if (!this.selectedFlight) {
      return;
    }
  
    const ticketContainer = this.ticketContainer.nativeElement;
  
    domtoimage.toBlob(ticketContainer).then((blob: any) => {
      const link = document.createElement('a');
      link.href = URL.createObjectURL(blob);
      link.download = 'ticket.png';
      link.click();
    });
  }
  
}
