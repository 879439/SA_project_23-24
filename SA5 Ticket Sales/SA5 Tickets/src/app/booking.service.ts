// booking.service.ts

import { Injectable } from '@angular/core';
import { Flight } from './flight.model';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, catchError, of, tap } from 'rxjs';
import { FlightService } from './flight.service';

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  private bookings: any[] = [];
  private apiUrl = "http://localhost:8080/api/bookings";
  headers = new HttpHeaders().set("Authorization", "Bearer"+" eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnYXR0byIsImlhdCI6MTcwNjI3MjkwMCwiZXhwIjoxNzA2NDQ1NzAwfQ.xAw3QEEaQ-fY2xPm9pUiNsheauV3GqEh1U-DlJKEIYI");
  constructor(private http:HttpClient, private flightService:FlightService) { }

  bookFlight(passengers:any[],flight: any,type:string):Observable<any> {

    // Simulate saving the booking data (you can replace this with server API calls)
    const booking = {
      people: passengers,
      flightId1: flight.flight1.id,
      flightId2: flight.flight2!=null ? flight.flight2.id : null,
      type:type,
      date: new Date(),
    };
    this.bookings.push(booking);
    return this.http.post(this.apiUrl+"/flight",booking).pipe(tap((bookings: any) => {
      console.log('fetched booking');
      
    }),
    catchError(this.handleError('getBooking', [])));
  }
  getTicket(bookingId:any):Observable<any>{
    return this.http.get(this.apiUrl+"/ticket",{params: new HttpParams().set("bookingId",bookingId), responseType: 'blob'},).pipe(tap((ticket: any) => {
      console.log('fetched ticket');
      console.log(ticket);
      
    }),
    catchError(this.handleError('getTicket', [])));
  }
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
  getMyBookings(){
    return this.http.get(this.apiUrl+"/myBookings",{headers:this.headers}).pipe(tap((bookings: any) => {
      console.log('fetched booking');
      console.log(bookings);
      
    }),
    catchError(this.handleError('getMyBooking', [])));
  }
  getBookings() {
    return this.bookings;
  }
}
