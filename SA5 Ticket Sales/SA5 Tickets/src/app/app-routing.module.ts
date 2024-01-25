import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { SearchFlightComponent } from './search-flight/search-flight.component';
import { SearchResultsComponent } from './search-results/search-results.component';
import { CustomerDetailsComponent } from './customer-details/customer-details.component';
import { ViewTicketComponent } from './view-ticket/view-ticket.component';
import { DownloadTicketComponent } from './download-ticket/download-ticket.component';
import { BookingFormComponent } from './booking-form/booking-form.component';
import { MyBookingComponent } from './my-booking/my-booking.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: '', component: SearchFlightComponent },
  { path: 'search-results', component: SearchResultsComponent },
  { path: 'customer-details', component: CustomerDetailsComponent },
  { path: 'view-ticket', component: ViewTicketComponent },
  { path: 'download-ticket', component: DownloadTicketComponent },
  { path: 'booking-form', component: BookingFormComponent },
  { path: 'my-booking', component: MyBookingComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
