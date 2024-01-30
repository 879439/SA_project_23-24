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
  private apiUrl = "http://localhost:8082/api/bookings";
  
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
    sessionStorage.setItem("booking",JSON.stringify(booking));
    console.log(sessionStorage.getItem("jwt"))
    const headers = new HttpHeaders().set("Authorization", "Bearer "+sessionStorage.getItem("jwt") );
    return this.http.post(this.apiUrl+"/flight",booking,{headers: headers}).pipe(tap((bookings: any) => {
      console.log('fetched booking');
      
    }),
    catchError(this.handleError('getBooking', [])));
  }
  getTicket(bookingId:any):Observable<any>{
    return this.http.get("http://localhost:8083"+"/ticket",{params: new HttpParams().set("bookingId",bookingId), responseType: 'blob'},).pipe(tap((ticket: any) => {
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
    const headers = new HttpHeaders().set("Authorization", "Bearer "+sessionStorage.getItem("jwt") );
    console.log("My jwt"+sessionStorage.getItem("jwt"))
    return this.http.get(this.apiUrl+"/myBookings",{headers:headers}).pipe(tap((bookings: any) => {
      console.log('fetched booking');
      console.log(bookings);
      
    }),
    catchError(this.handleError('getMyBooking', [])));
  }
  deleteBooking(id:string){
    const headers = new HttpHeaders().set("Authorization", "Bearer "+sessionStorage.getItem("jwt") );
    console.log("My jwt"+sessionStorage.getItem("jwt"))
    return this.http.delete(this.apiUrl+"/"+id,{headers:headers}).pipe(tap((booking: any) => {
      console.log('delete booking');
      console.log(booking);
      
    }),
    catchError(this.handleError('getMyBooking', [])));
  }
  getByBookingId(id:string){
    return this.http.get(this.apiUrl+"/"+id).pipe(tap((booking: any) => {
      console.log('fetched booking');
      console.log(booking);
      
    }),
    catchError(this.handleError('getMyBooking', [])));
  }
  getBookings() {
    return this.bookings;
  }
}
