// booking.service.ts

import { Injectable } from '@angular/core';
import { Flight } from './flight.model';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, of, tap } from 'rxjs';
import { FlightService } from './flight.service';

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  private bookings: any[] = [];
  private apiUrl = "http://localhost:8080/api/bookings";
  constructor(private http:HttpClient, private flightService:FlightService) { }

  bookFlight(passengers:any[],flight: any,type:string):Observable<any[]> {

    // Simulate saving the booking data (you can replace this with server API calls)
    const booking = {
      people: passengers,
      flightId1: flight.flight1.id,
      flightId2: flight.flight2.id,
      type:type,
      date: new Date(),
    };
    this.bookings.push(booking);
    return this.http.post(this.apiUrl+"/flight",booking).pipe(tap((bookings: any) => {
      console.log('fetched booking');
      bookings.array.forEach((booking: { flightId1: string; }) => {
        this.flightService.getFlightsById(booking.flightId1).subscribe(flight =>{

        });
      });
      
    }),
    catchError(this.handleError('getBooking', [])));
  }
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  getBookings() {
    return this.bookings;
  }
}
