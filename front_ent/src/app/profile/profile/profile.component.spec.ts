import { ComponentFixture, TestBed } from '@angular/core/testing';

import { profileComponent } from './profile.component';

describe('ProfileComponent', () => {
  let component: profileComponent;
  let fixture: ComponentFixture<profileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [profileComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(profileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
