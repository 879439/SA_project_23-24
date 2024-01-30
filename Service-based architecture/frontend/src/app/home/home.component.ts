import { Component } from '@angular/core';
import { SearchFlightComponent } from '../search-flight/search-flight.component';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  constructor() { 

  };

  ngOnInit() {
    // You can now access properties or call methods of SearchFlightComponent
    
  }
  get isAdmin(): boolean {
    console.log("Can you see me?")
    const user = JSON.parse(sessionStorage.getItem('user')+"");
    console.log(user)
    return (user !== null && user.role==='admin') ? true : false;
  }
}

