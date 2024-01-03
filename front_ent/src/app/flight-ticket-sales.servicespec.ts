import { TestBed } from '@angular/core/testing';

import { FlightTicketSalesService } from './flight-ticket-sales.service';

describe('FlightTicketSaleservice', () => {
  let service: FlightTicketSalesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FlightTicketSalesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});