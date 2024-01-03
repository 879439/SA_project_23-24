import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { faPlaneDeparture } from '@fortawesome/free-solid-svg-icons';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.sass'
})
export class AppComponent {
  title = 'Flight-Ticket-Sales';
  faPlaneDeparture = faPlaneDeparture;
  url: string = 'http://django-env.eba-wgpvstzd.us-east-1.elasticbeanstalk.com/admin/';
//faPlaneDeparture: any;

  goToAdminPortal(){
    window.open(this.url, "_blank");
  }
}
