// app.routes.ts

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddPassengerComponent } from './add-passenger/add-passenger/add-passenger.component';
import { SearchComponent } from './search-results/search/search.component';
import { FlightDetailsComponent } from './flight-details/flight-details/flight-details.component';
import { profileComponent } from './profile/profile/profile.component';
import { BookingComponent } from './booking/booking/booking.component';
import { NotificationComponent } from './notification/notification/notification.component';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { LoaderComponent } from './loader/loader.component';
import { AppComponent } from './app.component';


export const routes: Routes = [
  { path: 'AddPassengerComponent', component: AddPassengerComponent },
  { path: 'search-results', component: SearchComponent },
  { path: 'flight-details', component: FlightDetailsComponent },
  { path: 'profile', component: profileComponent },
  { path: 'booking', component: BookingComponent },
  { path: 'notifications', component: NotificationComponent },
  // Add more routes as needed
];
@NgModule({
  imports: [RouterModule.forRoot(routes),CommonModule,ReactiveFormsModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }

///:id(from flight-details ending path)