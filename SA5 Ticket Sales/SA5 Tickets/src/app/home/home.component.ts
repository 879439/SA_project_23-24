import { Component } from '@angular/core';
import { SearchFlightComponent } from '../search-flight/search-flight.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  constructor(private searchFlightComponent: SearchFlightComponent) { };

  ngOnInit() {
    // You can now access properties or call methods of SearchFlightComponent
    console.log(this.searchFlightComponent.tripType);
  }
}
