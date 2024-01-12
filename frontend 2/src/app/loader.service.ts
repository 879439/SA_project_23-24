import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoaderService {
  constructor(){}
  public isLoading = new BehaviorSubject<boolean>(false);
  show() {
    console.log("In Interceptor: ", true);
    this.isLoading.next(true);
  }
  
  hide() {
    console.log("In Interceptor: ", false);
    this.isLoading.next(false);
  }
}

