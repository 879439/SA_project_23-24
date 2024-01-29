import { Component } from '@angular/core';
import { BookingService } from '../booking.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-download-ticket',
  templateUrl: './download-ticket.component.html',
  styleUrls: ['./download-ticket.component.scss']
})
export class DownloadTicketComponent {
  constructor(private bookingService:BookingService,private route: ActivatedRoute,private router: Router){}

  ngOnInit(){
    this.route.queryParams.subscribe(params => {
      //const { passengers,selectedFlight, passengerEmail, type,firstname, lastname, email,seatSelection } = params;
      const selectedFlight =JSON.parse( params["selectedFlight"]);
      const passengers = JSON.parse( params["passengers"]);
      const type = params["type"];
      console.log(selectedFlight)
      this.bookingService.bookFlight(
        passengers,
        selectedFlight,
        type
      ).subscribe(booking =>{
        console.log(booking);
        console.log("mine");
        this.bookingService.getTicket(booking.id).subscribe(data =>{
          console.log("my ticket")
          const blob = new Blob([data], { type: 'application/pdf' });

          // Crea un URL per il blob
          const url = window.URL.createObjectURL(blob);

          // Crea un elemento <a> temporaneo per il download
          const a = document.createElement('a');
          a.href = url;
          a.download = booking.id+".pdf";
          document.body.appendChild(a);
          a.click();

          // Rimuovi l'URL e l'elemento <a> dopo il download
          window.URL.revokeObjectURL(url);
          document.body.removeChild(a);
          this.router.navigate(['/booking-details'],{
            queryParams: {
              bookingId: booking.id 
            }});
        })
      });
      
    });
  }

}
