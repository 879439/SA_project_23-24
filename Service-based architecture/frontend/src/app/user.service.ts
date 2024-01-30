import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap, catchError, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = "http://localhost:8080/api/auth";
  constructor(private http:HttpClient) { }

  login(username:string, password:string): Observable<HttpResponse<any>> {
    
    return this.http.post<any[]>(this.apiUrl+"/signin",{username,password},{observe:"response"});
  }
  register(username:string,email:string, password:string, lastname:string, firstname:string, sex:string, birthday: string){
    return this.http.post(this.apiUrl+"/signup",{username,email,password,lastname,firstname,sex,birthday});
  }
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
 export interface User{
  username:string;
  password:string;
  sex:string;
  firstname:string;
  lastname:string;
  birthday:string;
  email:string;

 }