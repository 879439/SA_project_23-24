import { Component, OnInit } from '@angular/core';
import { BookingService } from '../booking.service';

@Component({
  selector: 'app-my-booking',
  templateUrl: './my-booking.component.html',
  styleUrls: ['./my-booking.component.scss'],
})
export class MyBookingComponent implements OnInit {
  bookings: any[] = [];

  constructor(private bookingService: BookingService) {}

  ngOnInit() {
    this.bookings = this.bookingService.getBookings();
  }
}
