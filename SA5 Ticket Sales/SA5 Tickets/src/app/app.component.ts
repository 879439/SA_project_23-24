import { Component } from '@angular/core';
import {  ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class AppComponent {
  title = 'fly8-buddy';

  get isLoggedIn(): boolean {
    const user = JSON.parse(sessionStorage.getItem('user')+"");
    return (user !== null) ? true : false;
  }

  logout(){
    sessionStorage.removeItem('user');
  }
}
