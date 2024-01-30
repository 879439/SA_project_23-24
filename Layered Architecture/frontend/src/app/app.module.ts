import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { SearchFlightComponent } from './search-flight/search-flight.component';
import { SearchResultsComponent } from './search-results/search-results.component';
import { DownloadTicketComponent } from './download-ticket/download-ticket.component';
import { BookingFormComponent } from './booking-form/booking-form.component';
import { MyBookingComponent } from './my-booking/my-booking.component';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './login/login.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BookingDetailsComponent } from './booking-details/booking-details.component';
import { AdminComponent } from './admin/admin.component';
import { FlightsListComponent } from './flights-list/flights-list.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SearchFlightComponent,
    SearchResultsComponent,
    DownloadTicketComponent,
    BookingFormComponent,
    MyBookingComponent,
    LoginComponent,
    BookingDetailsComponent,
    AdminComponent,
    FlightsListComponent,
    ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatSnackBarModule,
    FormsModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
