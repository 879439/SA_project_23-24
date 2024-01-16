import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, of, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  private apiUrl = "http://localhost:8080/api/bookings/";
  constructor(private http:HttpClient) { }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
  getBooking(id : number): Observable<any[]>{
    const httpParams = new HttpParams().set('booking_id', id.toString());
    return this.http.get<any[]>(this.apiUrl + 'getbookings/', {params: httpParams})
    .pipe(tap((routes: any) => console.log('Booking')),
        catchError(this.handleError('getBooking', [])));
  }

  createPassenger(data: any): Observable<any> {
    return this.http.post(this.apiUrl + 'createpassenger', data)
    .pipe(tap((routes: any) => console.log('createpassenger')),
        catchError(this.handleError('createpassenger', [])));
  }

  makeBooking(data: any): Observable<any> {
    return this.http.post(this.apiUrl + 'flight', data)
    .pipe(tap((routes: any) => console.log('createbooking')),
        catchError(this.handleError('createbooking', [])));
  }

  updateBooking(data: any): Observable<any> {
    return this.http.put(this.apiUrl + 'createbooking', data)
    .pipe(tap((routes: any) => console.log('updatebooking')),
        catchError(this.handleError('updatebooking', [])));
  }

  deleteBooking(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}createbooking/${id}`)
    .pipe(tap((routes: any) => console.log('deletebooking')),
        catchError(this.handleError('deletebooking', [])));
  }
  
}
