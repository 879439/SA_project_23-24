import { Component } from '@angular/core';
import { UserService } from '../user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  showRegistrationForm: boolean = false;

  // Login Form Fields
  loginUsername: string = '';
  loginPassword: string = '';

  // Registration Form Fields
  registerName: string = '';
  registerSurname: string = '';
  registerUsername: string = '';
  registerPassword:string='';
  registerSex:string='';
  registerDOB: string = '';
  registerEmail: string = '';
  agreeToTerms: boolean = false;
  constructor(private userService:UserService,private snackBar:MatSnackBar,private router:Router){}
  // Login Function
  login() {
    // Implement your login logic here
    this.userService.login(this.loginUsername, this.loginPassword).subscribe({
      next: (response) => {
        const jwt = response.headers.get('Authorization');
        if (jwt) {
          sessionStorage.setItem('jwt', jwt.replace('Bearer ', ''));
          sessionStorage.setItem("user",JSON.stringify(response.body));

          console.log(response.body)
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
        console.log('Login with:', this.loginUsername, this.loginPassword);
        this.router.navigate(['/']);
        this.snackBar.open('Login Successfully', 'Close', {
          duration: 3000, // duration in milliseconds
          horizontalPosition: 'right', // 'start' | 'center' | 'end' | 'left' | 'right'
          verticalPosition: 'top', // 'top' | 'bottom'
        });
      }
    });
    
  }

  // Registration Function
  register() {
    // Implement your registration logic here
    this.userService.register(this.registerUsername,this.registerEmail,this.registerPassword,this.registerSurname,this.registerName,this.registerSex,this.registerDOB).subscribe({
      next: (response) => {

      },
      error: (error) => {
        console.log(error)
        this.snackBar.open(error.message, 'Close', {
          duration: 3000, // duration in milliseconds
          horizontalPosition: 'right', // 'start' | 'center' | 'end' | 'left' | 'right'
          verticalPosition: 'top', // 'top' | 'bottom'
        });
      },
      complete: () => {
        this.snackBar.open('Register Successfully', 'Close', {
          duration: 3000, // duration in milliseconds
          horizontalPosition: 'right', // 'start' | 'center' | 'end' | 'left' | 'right'
          verticalPosition: 'top', // 'top' | 'bottom'
        });
      }
    });
    console.log('Register with:', this.registerName, this.registerSurname, this.registerDOB, this.registerEmail);
  }
}
