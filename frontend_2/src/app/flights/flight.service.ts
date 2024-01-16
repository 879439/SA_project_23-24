import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, of, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FlightService {

  private apiUrl = "http://localhost:8080/api/flights";
  constructor(private http:HttpClient) { }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  getFlights(id: number, departDate?: any, returnDate?: string, returnId?: any): Observable<any[]> {
   
    let httpParams;
   if(returnDate) {
     httpParams = new HttpParams().set('route_id', id.toString()).set('depart_date', departDate)
      .set('return_date', returnDate).set('return_route_id', returnId.toString());
    } else if(id && departDate) {
      httpParams = new HttpParams().set('route_id', id.toString()).set('depart_date', departDate);
    } else {
      httpParams = new HttpParams().set('route_id', id.toString());
    }
    return this.http.get<any[]>(this.apiUrl + 'gettrips/', {params: httpParams})
    .pipe(tap((flights: any) => console.log('fetched trips')),
      catchError(this.handleError('getFlights', [])));
  }
  getFoodPref(): Observable<any[]>{
    return this.http.get<any[]>(this.apiUrl + 'getfood/')
    .pipe(tap((routes: any) => console.log('fetched food names')),
        catchError(this.handleError('getFoodPref', [])));
  }
  getAvailableSeats(tripId: number): Observable<any[]>{
    const httpParams = new HttpParams().set('trip_id', tripId.toString());
    return this.http.get<any[]>(this.apiUrl + 'getavailseats',{params: httpParams})
    .pipe(tap((routes: any) => console.log('Available seats')),
        catchError(this.handleError('getAvailableSeats', [])));
  }
  getFlightsByCities(departure:String,arrival:String): Observable<any[]>{
    return this.http.get<any[]>(`${this.apiUrl}/${departure}/${arrival}`);
  }
  getFlightById(id:String):Observable<any>{
    return this.http.get<any[]>(this.apiUrl+"/"+id);
  }
}
