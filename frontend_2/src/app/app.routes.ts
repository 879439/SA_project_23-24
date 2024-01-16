import { Routes } from '@angular/router';
import { LoginComponent } from './user/login/login.component';
import { RegisterComponent } from './user/register/register.component';
import { ProfileComponent } from './user/profile/profile.component';
import { BookingComponent } from './bookings/booking/booking.component';
import { FlightComponent } from './flights/flight/flight.component';
import { AddPassengerComponent } from './bookings/add-passenger/add-passenger.component';
import { BookflightComponent } from './bookings/bookflight/bookflight.component';

export const routes: Routes = [
    {path:"login",component:LoginComponent},
    {path:"register",component:RegisterComponent},
    {path:"profile",component:ProfileComponent},
    {path: 'addPassenger/:flightId', component: AddPassengerComponent },
    {path: 'flight', component: FlightComponent },
    {path: 'booking', component: BookingComponent },
    {path:"bookFlight/:flightId", component:BookflightComponent}

];
