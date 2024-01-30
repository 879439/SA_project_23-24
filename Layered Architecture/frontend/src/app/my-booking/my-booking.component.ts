import { Component, OnInit } from '@angular/core';
import { BookingService } from '../booking.service';
import { FlightService } from '../flight.service';
import { HomeComponent } from '../home/home.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-my-booking',
  templateUrl: './my-booking.component.html',
  styleUrls: ['./my-booking.component.scss'],
})
export class MyBookingComponent implements OnInit {
  bookings: any[] = [];

  constructor(private bookingService: BookingService, private flightService:FlightService, private router:Router) {}

  ngOnInit() {
    this.bookingService.getMyBookings().subscribe(bookings =>{
        
          this.bookings = bookings;
      
    });
  }
  get isAdmin(): boolean {
    const user = JSON.parse(sessionStorage.getItem('user')+"");
    return (user !== null && user.role==='admin') ? true : false;
  }
  deleteBooking(id:string){
    this.bookingService.deleteBooking(id).subscribe(()=>{
        this.reloadPage();
    })
  }
  reloadPage() {
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigate([window.location.pathname]);
    });
  }
}
