import { Component } from '@angular/core';
import { BookingService } from '../booking.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-booking-details',
  templateUrl: './booking-details.component.html',
  styleUrls: ['./booking-details.component.scss']
})
export class BookingDetailsComponent {
  booking!: any[];
  constructor(private bookingService:BookingService,private route: ActivatedRoute,private router: Router){}

  ngOnInit(){
    this.route.queryParams.subscribe(params => {
      const id = params['bookingId'];
      this.bookingService.getByBookingId(id).subscribe(booking =>{
        console.log(booking)
        this.booking = booking;
      })
    });
    
  }

  downloadTicket(id:string){
    this.bookingService.getTicket(id).subscribe(data =>{
      console.log("my ticket")
      const blob = new Blob([data], { type: 'application/pdf' });

      // Crea un URL per il blob
      const url = window.URL.createObjectURL(blob);

      // Crea un elemento <a> temporaneo per il download
      const a = document.createElement('a');
      a.href = url;
      a.download = id+".pdf";
      document.body.appendChild(a);
      a.click();

      // Rimuovi l'URL e l'elemento <a> dopo il download
      window.URL.revokeObjectURL(url);
      document.body.removeChild(a);
    })
  }
  deleteBooking(id:string){
    this.bookingService.deleteBooking(id).subscribe(()=>{
        this.router.navigate(["/"]);
    })
  }
}
