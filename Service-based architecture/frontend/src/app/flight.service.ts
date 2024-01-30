// flight.service.ts

import { Injectable } from '@angular/core';
import { Observable, catchError, of, tap } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Flight } from './flight.model';

@Injectable({
  providedIn: 'root'
})
export class FlightService {
  private apiUrl = "http://localhost:8081/api/flights";
  constructor(private http:HttpClient) { }

  /*getMockFlights(): Observable<any[]> {
    // Generate mock flight data
    /*const flights: Flight[] = [
      { id: 1230924, origin: 'New York', destination: 'Los Angeles', date: '2023-08-15', price: 100 },
      { id: 290796, origin: 'Chicago', destination: 'Miami', date: '2023-08-20', price: 85 },
      { id: 335677, origin: 'San Francisco', destination: 'Seattle', date: '2023-08-25', price: 50 },
      { id: 412345, origin: 'Chicago', destination: 'Los Angeles', date: '2023-08-15', price: 120 },
      { id: 524676, origin: 'Bhubaneswar', destination: 'Hydrabad', date: '2023-08-20', price: 180 },
      { id: 656980, origin: 'Venice', destination: 'Milan', date: '2023-08-25', price: 220 },
      { id: 738104, origin: 'Rome', destination: 'Florence', date: '2023-08-15', price: 250 },
      { id: 825634, origin: 'Rome', destination: 'Venice', date: '2023-08-20', price: 180 },
      { id: 935445, origin: 'Venice', destination: 'Rome', date: '2023-08-25', price: 120 },
      { id: 104588, origin: 'New York', destination: 'Miami', date: '2023-08-15', price: 250 },
      { id: 124146, origin: 'Chicago', destination: 'Miami', date: '2023-08-20', price: 120 },
      { id: 132345, origin: 'Miamio', destination: 'Seattle', date: '2023-08-25', price: 420 },
      // Add more mock flights here
    ];
    return of(flights);
  }*/
  getOneWayFlights(from:string, to:string, departureDate:string,travelClass:string,nPerson:number): Observable<Flight[]> {
    
    let httpParams;
    
     httpParams = new HttpParams().set('departure', from).set('arrival', to)
      .set('date', departureDate).set('travelClass', travelClass).set("nPerson",nPerson);
      console.log(httpParams)
    return this.http.get<any[]>(this.apiUrl+ '/one-way', {params: httpParams})

    .pipe(tap((flights: any) => console.log('fetched trips')),
      catchError(this.handleError('getFlights', [])));
  }
  getRoundTripFlights(from:string, to:string, departureDate:string,travelClass:string,nPerson:number,returnDate:string): Observable<any[]> {
    
    let httpParams;
    
     httpParams = new HttpParams().set('departure', from).set('arrival', to)
      .set('date', departureDate).set('travelClass', travelClass).set("nPerson",nPerson).set("returnDate",returnDate);
      console.log(httpParams)
    return this.http.get<any[]>(this.apiUrl+ '/round-trip', {params: httpParams})

    .pipe(tap((flights: any) => console.log('fetched trips')),
      catchError(this.handleError('getFlights', [])));
  }
  getFlightsById(id:string): Observable<Flight[]> {
    
    return this.http.get<any[]>(this.apiUrl+ '/'+id,)
    .pipe(tap((flights: any) => console.log('fetched trips')),
      catchError(this.handleError('getFlights', [])));
  }
  getFlights(): Observable<Flight[]> {
    const headers = new HttpHeaders().set("Authorization", "Bearer "+sessionStorage.getItem("jwt") );
   
    return this.http.get<any[]>(this.apiUrl,{headers:headers})
    .pipe(tap((flights: any) => console.log('fetched trips')),
      catchError(this.handleError('getFlights', [])));
  }
  deleteFligth(id:string): Observable<Flight[]> {
    const headers = new HttpHeaders().set("Authorization", "Bearer "+sessionStorage.getItem("jwt") );
  
    return this.http.delete<any[]>(this.apiUrl+"/"+id,{headers:headers})
    .pipe(tap((flights: any) => console.log('fetched trips')),
      catchError(this.handleError('getFlight', [])));
  }
  putFlight(company:string, departure:string, arrival:string, date:string, departure_time:string,arrival_time:string,travelClass:string,foods:any,size:string){
    const headers = new HttpHeaders().set("Authorization", "Bearer "+sessionStorage.getItem("jwt") );
    console.log("My jwt"+sessionStorage.getItem("jwt"))
    return this.http.put(this.apiUrl,{company,departure,arrival,arrival_time,departure_time,date,travelClass,foods,size},{headers:headers}).pipe(tap((flight: any) => {
      console.log('fetched flight');
      console.log(flight);
      
    }),
    catchError(this.handleError('putFlight', [])));
  }
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
