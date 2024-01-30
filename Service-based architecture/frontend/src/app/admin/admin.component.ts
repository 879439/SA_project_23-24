import { Component } from '@angular/core';
import { FlightService } from '../flight.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent {
  company:string='';
  departure:string='';
  arrival:string='';
  date:string='';
  departure_time:string='';
  arrival_time:string='';
  travelClass:string='';
  name:string='';
  quantity:number=0;
  price:number=0;
  size:string='';
  discountCode:string='';
  constructor(private flightService:FlightService,private snackBar:MatSnackBar) {
    
  }
  insertFlight(){
    
    if(this.isFormValid()){
        this.flightService.putFlight(this.company,this.departure,this.arrival,this.date,this.departure_time,this.arrival_time,this.travelClass,[{name:this.name,quantity:this.quantity,price:this.price}],this.size).subscribe(flight =>{
          console.log(flight)
          this.snackBar.open('Flight inserted!', 'Close', {
            duration: 3000, // duration in milliseconds
            horizontalPosition: 'right', // 'start' | 'center' | 'end' | 'left' | 'right'
            verticalPosition: 'top', // 'top' | 'bottom'
          });
        })
        this.resetForm();
    }else {
      alert('Please fill in all required fields.');
    }
  }
  isFormValid(): boolean {
    return (
      this.company.trim() !== '' &&
      this.departure.trim() !== '' &&
      this.arrival.trim() !== '' &&
      this.date.trim() !== '' &&
      this.departure_time.trim() !== '' &&
      this.arrival_time.trim() !== '' &&
      this.travelClass.trim() !== '' &&
      this.name.trim() !== '' &&
      this.quantity>0 &&
      this.price >0 &&
      this.size.trim() !== '' 

    );
  }

  resetForm() {
    // Reset form and clear fields
    this.company = '';
    this.departure = '';
    this.arrival = '';
    this.date = '';
    this.departure_time = '';
    this.arrival_time = '';
    this.travelClass = '';
    this.name = '';
    this.quantity = 0;
    this.price = 0;
    this.size = '';
    this.discountCode = '';

  }
}
