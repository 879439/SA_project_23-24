import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BookingService } from '../booking.service';
import { Flight, Flights } from '../flight.model';
import html2canvas from 'html2canvas';
import domtoimage from 'dom-to-image';
import { DataSharingServiceService } from '../data-sharing-service.service';
import { DataSharingService } from '../data-sharing.service';

@Component({
  selector: 'app-view-ticket',
  templateUrl: './view-ticket.component.html',
  styleUrls: ['./view-ticket.component.scss']
})
export class ViewTicketComponent implements OnInit {
  bookingDetails: any;
  selectedFlight: Flights | null = null;

  @ViewChild('ticketContainer', { static: true }) ticketContainer!: ElementRef;

  constructor(private route: ActivatedRoute, private bookingService: BookingService,private dataSharingService: DataSharingService,) { }

  ngOnInit() {
    // Get the booking details from query parameters
    this.route.queryParams.subscribe(params => {
      //const { passengers,selectedFlight, passengerEmail, type,firstname, lastname, email,seatSelection } = params;
      const selectedFlight =JSON.parse( params["selectedFlight"]);
      const passengers = JSON.parse( params["passengers"]);
      const type = params["type"];
      console.log(selectedFlight)
      this.bookingService.bookFlight(
        passengers,
        selectedFlight,
        type
      ).subscribe(booking =>{
        console.log(booking);
        console.log(bookings);
        console.log("mine");
        this.bookingService.getTicket(booking.id).subscribe(data =>{
          console.log("my ticket")
          const blob = new Blob([data], { type: 'application/pdf' });

          // Crea un URL per il blob
          const url = window.URL.createObjectURL(blob);

          // Crea un elemento <a> temporaneo per il download
          const a = document.createElement('a');
          a.href = url;
          a.download = booking.id+".pdf";
          document.body.appendChild(a);
          a.click();

          // Rimuovi l'URL e l'elemento <a> dopo il download
          window.URL.revokeObjectURL(url);
          document.body.removeChild(a);
        })
      });
      const pnrNumber = this.generatePNRNumber();
      // Retrieve the selected flight based on flightId (you can fetch it from the FlightService or use the saved bookings)
      const bookings = this.bookingService.getBookings();
      console.log("view ticket page")
      console.log(bookings)
      
      //const selectedBooking = bookings.find(booking => booking.flight.id === flightId);

      // Set the booking details and selected flight
      this.bookingDetails = {
        //passengerName,
        //passengerEmail,
        //passengerPhone,
        //seatSelection,
       // flightId,
        pnrNumber,
        bookingDate: new Date().toLocaleDateString()
      };
      //this.selectedFlight = selectedBooking ? selectedBooking.flight : null;
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
