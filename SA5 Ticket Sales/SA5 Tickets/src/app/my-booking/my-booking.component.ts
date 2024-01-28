import { Component, OnInit } from '@angular/core';
import { BookingService } from '../booking.service';
import { FlightService } from '../flight.service';

@Component({
  selector: 'app-my-booking',
  templateUrl: './my-booking.component.html',
  styleUrls: ['./my-booking.component.scss'],
})
export class MyBookingComponent implements OnInit {
  bookings: any[] = [];

  constructor(private bookingService: BookingService, private flightService:FlightService) {}

  ngOnInit() {
    this.bookingService.getMyBookings().subscribe(bookings =>{
        
          this.bookings = bookings;
      
    });
  }
}
