// search-flight.component.ts

import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FlightService } from '../flight.service';

@Component({
  selector: 'app-search-flight',
  templateUrl: './search-flight.component.html',
  styleUrls: ['./search-flight.component.scss']
})
export class SearchFlightComponent {
  tripType: string = 'one-way';
  from: string = '';
  to: string = '';
  departureDate: string = '';
  returnDate: string='';
  numAdults: number = 1;
  numChildren: number = 0;
  travelClass: string = '';
  errorMessage: string = '';

  constructor(private router: Router, private flightService: FlightService) { }

  searchFlights() {
    // Check if any required field is empty
    if (!this.from || !this.to || !this.departureDate) {
      this.errorMessage = 'Please fill in all required fields.';
      return;
    }

    // Reset error message if no errors
    this.errorMessage = '';

    // Navigate to the SearchResultsComponent and pass search criteria as query parameters
    this.router.navigate(['/search-results'], {
      queryParams: {
        tripType: this.tripType,
        from: this.from,
        to: this.to,
        departureDate: this.departureDate,
        numAdults: this.numAdults,
        numChildren: this.numChildren,
        travelClass: this.travelClass,
        returnDate: this.returnDate
      }
    });
  }
}
