import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { FlightTicketSalesService } from '../../flight-ticket-sales.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

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
  templateUrl: './add-passenger.component.html',
  styleUrls: ['./add-passenger.component.sass']
})
export class AddPassengerComponent implements OnInit {
  addPassengerForm!: FormGroup;
  foodPrefs: any[] = [];
  state$!: Observable<object>;
  trip: any;
  availableSeats: any[] = [];

  constructor(
    private flightService: FlightTicketSalesService,
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
    this.flightService.createPassenger(obj).subscribe(
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
