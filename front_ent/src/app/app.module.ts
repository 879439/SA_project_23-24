// app.module.ts
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule }  from '@angular/router';
import { AppComponent } from './app.component';
import { ReactiveFormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { LoaderComponent } from './loader/loader.component';
import { LoaderInterceptor } from './loader.interceptor';
import { AddPassengerComponent } from './add-passenger/add-passenger/add-passenger.component';
import { SearchComponent } from './search-results/search/search.component';
import { profileComponent } from './profile/profile/profile.component';
import { BookingComponent } from './booking/booking/booking.component';
import { FlightDetailsComponent } from './flight-details/flight-details/flight-details.component';
import { AppRoutingModule } from './app.routes';
import { FlightTicketSalesService } from './flight-ticket-sales.service'; // Update the path based on your project structure


@NgModule({
  declarations: [
    AppComponent,
    AddPassengerComponent,
    SearchComponent,
    profileComponent,
    FlightDetailsComponent,
    BookingComponent,
    AppRoutingModule,
    LoaderComponent
   
  ],
  imports: [
    BrowserModule,
    RouterModule. forRoot([]),
    BrowserModule,
    AppRoutingModule,
    NgModule,
    FontAwesomeModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: LoaderInterceptor, multi: true, },
    FlightTicketSalesService,

  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
