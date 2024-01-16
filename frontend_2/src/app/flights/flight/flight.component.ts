import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { FlightService } from '../flight.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-flight',
  standalone: true,
  imports: [FormsModule,CommonModule,RouterModule],
  templateUrl: './flight.component.html',
  styleUrl: './flight.component.scss'
})
export class FlightComponent {
  departure: string = '';
  arrival:string='';
  searchResults: any[] = [];

  constructor(private flightservice: FlightService, private router:Router) {}

  onSearch() {
    this.flightservice.getFlightsByCities(this.departure,this.arrival).subscribe(
      data => this.searchResults = data,
      error => console.error(error)
    );
  }
  bookFlight(id:String){
    this.router.navigate(["/bookFlight",id]);
    console.log(id);

  }
}
