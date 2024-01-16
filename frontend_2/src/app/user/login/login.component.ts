import { Component } from '@angular/core';
import { UserService } from '../user.service';
import { FormsModule } from '@angular/forms';
import { CookieService } from 'ngx-cookie-service';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSnackBar } from '@angular/material/snack-bar'

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule,MatSnackBarModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  username:string="";
  password:string="";
  constructor(private userService:UserService, private snackBar:MatSnackBar ){
  }
  login(){
    this.userService.login(this.username, this.password).subscribe({
      next: (response) => {
        const jwt = response.headers.get('Authorization');
        if (jwt) {
          localStorage.setItem('JWT', jwt.replace('Bearer ', ''));
          localStorage.setItem("user",JSON.stringify(response.body));

        }
      },
      error: (error) => {
        this.snackBar.open('Wrong credentials', 'Close', {
          duration: 3000, // duration in milliseconds
          horizontalPosition: 'right', // 'start' | 'center' | 'end' | 'left' | 'right'
          verticalPosition: 'top', // 'top' | 'bottom'
        });
      },
      complete: () => {
        this.snackBar.open('Login Successfully', 'Close', {
          duration: 3000, // duration in milliseconds
          horizontalPosition: 'right', // 'start' | 'center' | 'end' | 'left' | 'right'
          verticalPosition: 'top', // 'top' | 'bottom'
        });
      }
    });
    this.username="";
    this.password="";
  }

}
