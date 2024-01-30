import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataSharingService {
  private passengersSource = new BehaviorSubject<any>(null);
  currentPassengers = this.passengersSource.asObservable();

  constructor() { }

  updatePassengers(passengers: any) {
    this.passengersSource.next(passengers);
  }
}
