import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Flight, Flights } from '../flight.model';
import { BookingService } from '../booking.service';
import { CurrencyPipe } from '@angular/common';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-booking-form',
  templateUrl: './booking-form.component.html',
  styleUrls: ['./booking-form.component.scss']
})
export class BookingFormComponent implements OnInit {
  selectedFlight: Flights=new Flights();
  passengers:any[]=[];
  searchResults: any;
  firstname="";
  lastname="";
  email="";
  birthday="";
  sex="";
  foodSelection="";
  seatSelection="";
  returnFoodSelection="";
  returnSeatSelection="";
  isAdult=true;
  type = "";
  numChildren:number=0;
  numAdults:number=0;
  n:number=0;
  currentPassengerIndex = 0;
  bookingService: BookingService;

  constructor(private route: ActivatedRoute, private router: Router, bookingService: BookingService) {
    this.bookingService = bookingService;
  }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      const flightId1 = params['flightId1'];
      const flightId2 = params['flightId2'];
      this.numAdults = params['numAdults'];
      this.numChildren = params['numChildren'];
      let i=0;
      this.n= Number(this.numAdults)+Number(this.numChildren);
      console.log("Number -->"+this.n);

      this.type = params['type'];
      const searchResults = JSON.parse(params['searchResults']);
      console.log("HIII")
      console.log(searchResults);
      console.log(flightId1);
      console.log(flightId2);
      if(this.type=="one-way"){
        this.selectedFlight.flight1 = searchResults.find((flight:any) => flight.id === flightId1);
      }else{
        this.selectedFlight = searchResults.find((flight:any) => flight.flight1.id === flightId1);
      }
      
      console.log(this.selectedFlight)
    });
  }
  addPassenger(){
    if(this.isFormValid()){
        this.passengers.push({
          firstname:this.firstname,
          lastname:this.lastname,
          email: this.email,
          birthday: this.birthday,
          sex: this.sex,
          foodSelection: this.foodSelection,
          seatSelection: this.seatSelection,
          returnFoodSelection : this.returnFoodSelection,
          returnSeatSelection: this.returnSeatSelection,
          isAdult:true

        });
        this.currentPassengerIndex++;
        console.log(this.passengers);
        this.resetForm();
    }else{
      alert('Please fill in all required fields.');
    }
    
  }
  submitBookingForm() {
    console.log(this.passengers);
    if(this.isFormValid()) {
      this.passengers.push({
        firstname:this.firstname,
        lastname:this.lastname,
        email: this.email,
        birthday: this.birthday,
        sex: this.sex,
        foodSelection: this.foodSelection,
        seatSelection: this.seatSelection,
        returnFoodSelection : this.returnFoodSelection,
        returnSeatSelection: this.returnSeatSelection,
        isAdult:true

      });
      console.log(this.passengers);
      this.bookingService.bookFlight(
        this.passengers,
        this.selectedFlight!,
        this.type
      ).subscribe(booking =>{
        console.log(booking);
      });

      /*this.router.navigate(['/view-ticket'], {
        queryParams: {
          firstname: this.firstname,
          lastname: this.lastname,
          email: this.email,
          seatSelection: this.seatSelection
        }
      });*/
    } else {
      alert('Please fill in all required fields.');
    }
  }

  isFormValid(): boolean {
    return (
      this.firstname.trim() !== '' &&
      this.lastname.trim() !== '' &&
      this.email.trim() !== '' &&
      this.sex.trim() !== '' &&
      this.birthday.trim() !== '' &&
      this.seatSelection.trim() !== '' 

    );
  }

  resetForm() {
    // Reset form and clear fields
    this.firstname = '';
    this.lastname = '';
    this.sex = '';
    this.seatSelection = '';
    this.foodSelection = '';
    this.email = '';
    this.birthday = '';

  }
}
