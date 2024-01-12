import { HttpClient,HttpHeaders,HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, retry } from 'rxjs';
import { UserModule } from './user.module';

@Injectable({
  providedIn: "root"
})
export class UserService {
  private apiUrl = "http://localhost:8080/api/auth";
  headers = new HttpHeaders().set("Authorization", `Bearer`+localStorage.getItem("JWT"));


  constructor(private http:HttpClient) { 

  }

  login(username:string, password:string): Observable<HttpResponse<any>> {
    return this.http.post(this.apiUrl+"/signin",{username,password},{observe:"response"});
  }
  register(username:string,email:string, password:string, lastname:string, firstname:string, sex:string, birthday: string , roles:string[]){
    return this.http.post(this.apiUrl+"/signup",{username,email,password,lastname,firstname,sex,birthday,roles});
  }
}
