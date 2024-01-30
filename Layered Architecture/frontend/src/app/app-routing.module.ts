import { NgModule } from '@angular/core';
import { Routes, RouterModule, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { SearchFlightComponent } from './search-flight/search-flight.component';
import { SearchResultsComponent } from './search-results/search-results.component';
import { DownloadTicketComponent } from './download-ticket/download-ticket.component';
import { BookingFormComponent } from './booking-form/booking-form.component';
import { MyBookingComponent } from './my-booking/my-booking.component';
import { LoginComponent } from './login/login.component';
import { BookingDetailsComponent } from './booking-details/booking-details.component';
import { AdminComponent } from './admin/admin.component';

const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'search-results', component: SearchResultsComponent },
  { path: 'download-ticket', component: DownloadTicketComponent },
  { path: 'booking-details',component:BookingDetailsComponent},
  { path: 'booking-form', component: BookingFormComponent },
  { path: 'my-booking', component: MyBookingComponent },
  {path: 'login', component: LoginComponent},
  { path: '**', redirectTo: '' } // Wildcard route
];




@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
 }
