import { Component } from '@angular/core';
import { Flight } from '../flight.model';
import { FlightService } from '../flight.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-flights-list',
  templateUrl: './flights-list.component.html',
  styleUrls: ['./flights-list.component.scss']
})
export class FlightsListComponent {
  flights: Flight[]=[];
  constructor( private flightService:FlightService,private router:Router) {
    
  }
  ngOnInit(){
    this.flightService.getFlights().subscribe(flights =>{
      this.flights = flights;
    })
  }
  deleteFlight(id:string){
    this.flightService.deleteFligth(id).subscribe(()=>{
      this.reloadPage()
    })
  }
  reloadPage() {
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigate([window.location.pathname]);
    });
  }

}
