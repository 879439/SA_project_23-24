import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FlightTicketSalesService } from '../../flight-ticket-sales.service';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.sass']
})
export class BookingComponent implements OnInit{
  state$!: Observable<object>;
  bookingSummary: any = {};
  bookingResponse: any = undefined;

  constructor(
    private flightService: FlightTicketSalesService, 
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
      'book_type':"One-Way",
      'trip_id': this.bookingSummary['trip']['id'], 
      'passenger_id': this.bookingSummary['passenger']['id']
    }
    this.bookingResponse = undefined;
    this.flightService.makeBooking(obj).subscribe((res: any) => {
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
    this.flightService.updateBooking(obj).subscribe((res: any) => {
      console.log(res);
      this.bookingResponse = res;
    }, (err: any) => {
      console.log(err);
    })
  }

}
