// search-results.component.ts

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Flight } from '../flight.model';
import { FlightService } from '../flight.service';

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.scss']
})
export class SearchResultsComponent implements OnInit {
  searchResults: any[] = [];
  tripType = "";
  constructor(private route: ActivatedRoute, private router: Router, private flightService: FlightService) { }

  ngOnInit() {
    // Get search criteria from query parameters
    this.route.queryParams.subscribe(params => {
      const { tripType, from, to, departureDate, numAdults, numChildren, travelClass , returnDate} = params;
      this.tripType = tripType;
      if(tripType==="one-way"){
        console.log("HEREEEE")
      // Call the service to get mock flight data based on search inputs
        this.flightService.getOneWayFlights(from,to,departureDate,travelClass,Number(numAdults)+Number(numChildren)).subscribe(flights => {
          
          flights.forEach(
            flight =>{
              flight.price = flight.seats[0].childrenPrice*numChildren+flight.seats[0].price*numAdults;
            }
          )
          this.searchResults = flights;
          console.log(this.searchResults);
          });
      }else{
        this.flightService.getRoundTripFlights(from,to,departureDate,travelClass,Number(numAdults)+Number(numChildren),returnDate).subscribe(flights => {
          
          flights.forEach(
            flight =>{
              flight.price = flight.flight1.seats[0].childrenPrice*numChildren+flight.flight1.seats[0].price*numAdults+flight.flight2.seats[0].childrenPrice*numChildren+flight.flight2.seats[0].price*numAdults;
            }
          )
          this.searchResults = flights;
          });
      }
      });
    
  };

  bookFlight(flight: Flight) {
    // Navigate to the BookingFormComponent and pass the selected flight and search results as query parameters
    this.router.navigate(['/booking-form'], {
      queryParams: {
        flightId1: flight.id,
        flightId2: flight.id,
        type: this.tripType,
        searchResults: JSON.stringify(this.searchResults) // Convert searchResults array to JSON string
      }
    });
  }
};
function flights(value: Flight[]): void {
  throw new Error('Function not implemented.');
}

