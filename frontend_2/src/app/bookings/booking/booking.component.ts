import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, map } from 'rxjs';
import { BookingService } from '../booking.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-booking',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './booking.component.html',
  styleUrl: './booking.component.scss'
})
export class BookingComponent {
  state$!: Observable<object>;
  bookingSummary: any = {};
  bookingResponse: any = undefined;

  constructor(
    private bookingSerivce: BookingService, 
    private activatedRoute: ActivatedRoute
    ) { }

  ngOnInit(): void {
    this.getRouteData();
  }

  //interface BookingSummary {
    // Define your properties here
   // data: any;
 //}

  getRouteData() {
    this.state$ = this.activatedRoute.paramMap.pipe(map(() => window.history.state));
    this.state$.subscribe(res => {
      if (res &&  ('data') in res) { 
        this.bookingSummary = res['data'];
        console.log("Booking Summary: ", this.bookingSummary);
      } else {
        console.log("No data found in window history state.");
      }
    });
  }

  selectDepartingFlight() {
    let obj = {
      'type':"one-way",
      'flightId1': this.bookingSummary['Flight']['id'], 
      'people': [this.bookingSummary['passenger']]
    }
    this.bookingResponse = undefined;
    this.bookingSerivce.makeBooking(obj).subscribe((res: any) => {
      console.log(res);
      this.bookingResponse = res;
    }, (err: any) => {
      console.log(err);
    })
  }

  updateFlight() {
    let obj = {
      'id': this.bookingSummary['id'],
      'book_type':"One-Way",
      'trip_id': this.bookingSummary['trip']['id'], 
      'passenger_id': this.bookingSummary['passenger']['id'],
    }
    this.bookingResponse = undefined;
    this.bookingSerivce.updateBooking(obj).subscribe((res: any) => {
      console.log(res);
      this.bookingResponse = res;
    }, (err: any) => {
      console.log(err);
    })
  }
}
