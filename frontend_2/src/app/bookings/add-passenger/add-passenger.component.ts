import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Observable, map } from 'rxjs';
import { FlightService } from '../../flights/flight.service';
import { ActivatedRoute, Router } from '@angular/router';
import { BookingService } from '../booking.service';
import { LoaderComponent } from '../../loader/loader.component';
import { CommonModule } from '@angular/common';

export interface Passenger {
  fname: string;
  lname: string;
  email: string;
  age: number;
  sex: string;
  seat_number: string;
  food_name: string;
}

@Component({
  selector: 'app-add-passenger',
  standalone: true,
  imports: [LoaderComponent, ReactiveFormsModule,CommonModule],
  templateUrl: './add-passenger.component.html',
  styleUrl: './add-passenger.component.scss'
})
export class AddPassengerComponent {
  addPassengerForm!: FormGroup;
  foodPrefs: any[] = [];
  state$!: Observable<object>;
  trip: any;
  availableSeats: any[] = [];

  constructor(
    private flightService: FlightService,
    private bookingService: BookingService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.createForm();
    this.getRouteData();
    this.getFoodPreference();
  }

  getFoodPreference() {
    this.flightService.getFoodPref().subscribe(
      (res: any[]) => {
        this.foodPrefs = res;
        console.log(res);
      },
      (err: any) => {
        console.log(err);
      }
    );
  }

  getAvailableSeats() {
    this.flightService.getAvailableSeats(this.trip['id']).subscribe(
      (res: any[]) => {
        this.availableSeats = res;
        console.log(res);
      },
      (err: any) => {
        console.log(err);
      }
    );
  }

  getRouteData() {
    this.state$ = this.activatedRoute.paramMap.pipe(map(() => window.history.state));
    this.state$.subscribe((res) => {
      if (res && 'data' in res) {
        this.trip = res['data'];
        this.getAvailableSeats();
        console.log(res['data']);
      }
    });
  }

  createForm() {
    this.addPassengerForm = new FormGroup({
      firstName: new FormControl(''),
      lastName: new FormControl(''),
      email: new FormControl(''),
      age: new FormControl(''),
      sex: new FormControl(''),
      foodName: new FormControl(''),
      chooseSeats: new FormControl('')
    });
  }

  buildPassengerObj() {
    const obj: Passenger = {
      fname: this.addPassengerForm.controls['firstName'].value,
      lname: this.addPassengerForm.controls['lastName'].value,
      email: this.addPassengerForm.controls['email'].value,
      age: this.addPassengerForm.controls['age'].value,
      sex: this.addPassengerForm.controls['sex'].value,
      seat_number: this.addPassengerForm.controls['chooseSeats'].value,
      food_name: this.addPassengerForm.controls['foodName'].value
    };
    this.bookingService.createPassenger(obj).subscribe(
      (res: any) => {
        console.log(res);
        let routeData = { trip: this.trip, passenger: res };
        this.router.navigate(['/booking-summary'], { state: { data: routeData } });
      },
      (err: any) => {
        console.log(obj);
      }
    );
  }
}
