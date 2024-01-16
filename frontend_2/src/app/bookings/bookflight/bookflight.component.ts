import { Component } from '@angular/core';
import { LoaderComponent } from '../../loader/loader.component';
import { ActivatedRoute } from '@angular/router';
import { FlightService } from '../../flights/flight.service';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { FormGroup } from '@angular/forms';
import { FormControl } from '@angular/forms';
import { Passenger } from '../add-passenger/add-passenger.component';
import { BookingService } from '../booking.service';
import { RouterModule } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'app-bookflight',
  standalone: true,
  imports: [LoaderComponent,CommonModule,ReactiveFormsModule, RouterModule],
  templateUrl: './bookflight.component.html',
  styleUrl: './bookflight.component.scss'
})
export class BookflightComponent {
  flight:any;
  seat_price:number=0.0;
  food_price:number=0.0;
  food_selected:any;
  seat_selected:any;
  price:number=0.0;
  bookFlightForms!:FormGroup;
  user:any =JSON.parse(localStorage.getItem("user")||"");
  trip: any;
  selectControl:FormControl = new FormControl();
  selectControl2:FormControl = new FormControl();
  constructor(private router: Router, private flightSerive:FlightService,private bookingService:BookingService, private route:ActivatedRoute) {
    this.selectControl.valueChanges.subscribe((price: any) => {
      this.flight.seats.forEach((seat: { name: any; price: any; }) => {
        if(seat.name==price){
          this.seat_price = Number(seat.price);
          this.price = this.seat_price + this.food_price;
          this.seat_selected=seat.name;
        }
      });
      
      // Aggiorna qui la tua variabile o esegui altre azioni
    });
    this.selectControl2.valueChanges.subscribe((price: any) => {
      this.flight.foods.forEach((food: { name: any; price: any; }) => {
        if(food.name==price){
          this.food_price = Number(food.price);
          this.price = this.seat_price + this.food_price;
          this.food_selected= food.name;
        }
      });
      // Aggiorna qui la tua variabile o esegui altre azioni
    });
   }

  ngOnInit() {
    this.createForm();
    
    
    this.route.params.subscribe((params: { [x: string]: any; }) => {
      const id = params['flightId'];
        this.flightSerive.getFlightById(id).subscribe(
          data => {
            this.flight = data;
            console.log(data);
          },
          error => console.error(error)
        );
     });
  }
  createForm() {
    this.bookFlightForms = new FormGroup({
      firstName: new FormControl(this.user.firstname),
      lastName: new FormControl(this.user.lastname),
      email: new FormControl(this.user.email),
      birthday: new FormControl(this.user.birthday),
      sex: new FormControl(this.user.sex),
      foodName: new FormControl(''),
      chooseSeats: new FormControl('')
    });
  }

  makeBooking(){
    const obj ={ 
    passenger :{
      firstname: this.bookFlightForms.controls['firstName'].value,
      lastname: this.bookFlightForms.controls['lastName'].value,
      email: this.bookFlightForms.controls['email'].value,
      age: this.bookFlightForms.controls['birthday'].value,
      sex: this.bookFlightForms.controls['sex'].value,
      seat: this.seat_selected,
      food: this.food_selected

    },
    Flight :{
      id:this.flight.id,
      company: this.flight.company,
      departure: this.flight.departure,
      arrival: this.flight.arrival,
      date: this.flight.date,
      arrival_time: this.flight.arrival_time,
      departure_time: this.flight.departure_time,
      price:this.price
    }};
    console.log(obj)
    this.router.navigate(['/booking'], { state: { data: obj } });
  }
  priceChanging(price:any){
    this.price=+ price;
    console.log("This is the price-->"+price)
  }
}
