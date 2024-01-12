import { Injectable } from "@angular/core";
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Observable } from "rxjs";
import { finalize } from "rxjs/operators";
import { FlightTicketSalesService } from './flight-ticket-sales.service';

@Injectable({
    providedIn: 'root'
})

export class LoaderInterceptor implements HttpInterceptor {

    constructor(public FlightService: FlightTicketSalesService) { }

    //This interceptor will change the subject value to true, when a request starts, 
    //and hide it when the request is finalized.
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        this.FlightService.show(); //changing value to true
        return next.handle(request).pipe(
            finalize(() => this.FlightService.hide()) //changing value to false
        );
    }

    //The cool thing is that it calls our callback function on both success and error responses. 
    //This way, we can be sure that our application will not end up with non-stop spinning loader.
}