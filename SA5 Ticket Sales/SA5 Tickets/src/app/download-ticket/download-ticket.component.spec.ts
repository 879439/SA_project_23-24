import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DownloadTicketComponent } from './download-ticket.component';

describe('DownloadTicketComponent', () => {
  let component: DownloadTicketComponent;
  let fixture: ComponentFixture<DownloadTicketComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DownloadTicketComponent]
    });
    fixture = TestBed.createComponent(DownloadTicketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
