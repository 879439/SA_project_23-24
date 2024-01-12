import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';
import { FlightTicketSalesService } from '../flight-ticket-sales.service';

@Component({
  selector: 'app-loader',
  templateUrl: './loader.component.html',
  styleUrls: ['./loader.component.scss']
})
export class LoaderComponent implements OnInit, OnDestroy {

  isLoading: Subject<boolean> | undefined;
  //isShowing: boolean = false;
  
  //We can also subscribe() and unsubscribe() to above isLoading subject
  //to avoid memory leaks, but using "async" pipe in html is more cleaner way

  constructor(private flightService: FlightTicketSalesService) { }

  ngOnInit(): void {
    this.isLoading = this.flightService.isLoading;

    // this.airlineService.isLoading.subscribe(res => {
    //   console.log("Value of isLoading ", res);
    //   this.isShowing = res;
    // });
  }

  ngOnDestroy(): void {
    //this.isShowing = false;
  }

}
