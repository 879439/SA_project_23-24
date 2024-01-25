import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Flight, Flights } from '../flight.model';
import { BookingService } from '../booking.service';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-booking-form',
  templateUrl: './booking-form.component.html',
  styleUrls: ['./booking-form.component.scss']
})
export class BookingFormComponent implements OnInit {
  selectedFlight: Flights=new Flights();
  firstname: string = '';
  email: string = '';
  lastname: string = '';
  birthday: string = '';
  sex: string = 'Male';
  seatSelection: string = '';
  foodSelection: string = '';
  searchResults: any;
  type = "";
  bookingService: BookingService;

  constructor(private route: ActivatedRoute, private router: Router, bookingService: BookingService) {
    this.bookingService = bookingService;
  }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      const flightId1 = params['flightId1'];
      const flightId2 = params['flightId2'];
      this.type = params['type'];
      const searchResults = JSON.parse(params['searchResults']);
      console.log("HIII")
      console.log(searchResults);
      console.log(flightId1);
      console.log(flightId2);
      this.selectedFlight.flight1 = searchResults.find((flight: { id: number }) => flight.id === flightId1);
      this.selectedFlight.flight2 = searchResults.find((flight: { id: number }) => flight.id === flightId2);
      console.log(this.selectedFlight)
    });
  }

  submitBookingForm() {
    if (this.isFormValid()) {
      this.bookingService.bookFlight(
        this.firstname,
        this.lastname,
        this.birthday,
        this.email,
        this.sex,
        this.foodSelection,
        this.seatSelection,
        this.selectedFlight!,
        this.type
      ).subscribe(booking =>{
        console.log(booking);
      });

      this.router.navigate(['/view-ticket'], {
        queryParams: {
          firstname: this.firstname,
          lastname: this.lastname,
          email: this.email,
          seatSelection: this.seatSelection
        }
      });
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
