import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { faCalendarAlt } from '@fortawesome/free-solid-svg-icons';
import { FlightTicketSalesService } from '../flight-ticket-sales.service';
//import { cloneDeep, uniqBy } from 'lodash';
import { Router } from '@angular/router';


@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrl: './search.component.sass'
})
export class SearchComponent  implements OnInit{
  
  //searchFlightForm: FormGroup | any;
  faCalendarAlt = faCalendarAlt;
  //routes: any[] = undefined;
  departureRoutes: any[] = [];
  arrivalRoutes: any[] = [];
  modifyFlightForm: FormGroup | any
  booking: any = undefined;
  searchFlightForm: FormGroup<{ departureCity: FormControl<any>; arrivalCity: FormControl<any>; departDate: FormControl<any>; }> | any;
  routes: any;

  constructor(private flightService: FlightTicketSalesService, private router: Router) { }

  ngOnInit(): void {
    this.createForm();
    this.flightService.getRoutes().subscribe((res: any[]) => {
      this.routes = res;
    }, (err: any) => {
      console.log(err);
    });
    this.createModifyFlightForm();
  }

  createForm() {
    this.searchFlightForm = new FormGroup({
      departureCity: new FormControl(),
      arrivalCity: new FormControl(),
      departDate: new FormControl(),
      //returnDate: new FormControl()
    });
  }

  createModifyFlightForm(){
    this.modifyFlightForm = new FormGroup({
      modifyBooking: new FormControl(),
    });
  }

  getFlights() {

    if (this.searchFlightForm && this.searchFlightForm.controls && this.searchFlightForm.controls['departDate']) {
    let obj: any = {};
    obj['departDate'] = this.searchFlightForm.controls['departDate'].value.year + '-' +
                        this.searchFlightForm.controls['departDate'].value.month + '-' +
                        this.searchFlightForm.controls['departDate'].value.day;
    obj['id'] = this.getRouteId();
  
   this.getTrips(obj['id'], obj['departDate']);
    }
  }

  getTrips(id: number, departDate?: string, returnDate?: string, returnId?: number) {
    this.flightService.getFlights(id, departDate, returnDate, returnId)
      .subscribe((res: any) => {
        console.log(res);
        this.router.navigate(['/flights-view'], {state: {data: res}});
      }, (err: any) => {
        console.log(err);
      });
  }

  getDepartureRoutesOnPage() {
    if (this.searchFlightForm && this.router && this.searchFlightForm.controls['departureCity'].value.length == 2) {
      this.departureRoutes = [];
      this.routes.filter((element: { departure_city: string; }) => {
        if(element.departure_city.toLowerCase().includes(this.searchFlightForm.controls['departureCity'].value.toLowerCase())) {
          this.departureRoutes.push(element);
        }
      })
      this.departureRoutes = cloneDeep(uniqBy(this.departureRoutes, 'departure_city'));
      console.log(this.departureRoutes);
    }
  }

  getArrivalRoutesOnPage() {
    if (this.searchFlightForm) {
      this.arrivalRoutes = [];
      this.routes.filter((element: { departure_city: any; }) => {
        if(element.departure_city === this.searchFlightForm.controls['departureCity'].value) {
          this.arrivalRoutes.push(element);
        }
      })
      this.arrivalRoutes = cloneDeep(uniqBy(this.arrivalRoutes, 'arrival_city'));
      console.log(this.arrivalRoutes);
    }
  }

  getRouteId(): number | undefined {
    if(this.searchFlightForm) {
      let id;
      this.routes.forEach((element: { departure_city: any; arrival_city: any; id: any; }) => {
        if(element.departure_city === this.searchFlightForm.controls['departureCity'].value &&
        element.arrival_city === this.searchFlightForm.controls['arrivalCity'].value) {
          id = element.id;
        }
      })
      return id;
    }
    return undefined;
  }

  getReturnRouteId(): number | undefined {
    if(this.searchFlightForm && this.routes) {
      let id;
      this.routes.forEach((element: { departure_city: any; arrival_city: any; id: any; }) => {
        if(element.departure_city === this.searchFlightForm.controls['arrivalCity'].value &&
        element.arrival_city === this.searchFlightForm.controls['departureCity'].value) {
          id = element.id;
          
        }

      })
      return id;
    }
    return undefined;
  }

  getFlightBooking(){
    if (this.modifyFlightForm.controls['modifyBooking'].value){
      this.flightService.getBooking(this.modifyFlightForm.controls['modifyBooking'].value).subscribe((res: string | any[]) => {
      if(res && res.length > 0){
        this.booking = res;
        console.log(this.booking);
        this.router.navigate(['/flights-view'], {state: {booking: this.booking}});
      } else {
        this.booking = [];
      }
      }, (err: any) => {
        console.log(err);
      });
    }
  }
}




function uniqBy(departureRoutes: any[], arg1: string): any {
  throw new Error('Function not implemented.');
}

function cloneDeep(arg0: any): any[] {
  throw new Error('Function not implemented.');
}

