import { Component } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  showRegistrationForm: boolean = false;

  // Login Form Fields
  loginEmail: string = '';
  loginPassword: string = '';

  // Registration Form Fields
  registerName: string = '';
  registerSurname: string = '';
  registerDOB: string = '';
  registerEmail: string = '';
  agreeToTerms: boolean = false;

  // Login Function
  login() {
    // Implement your login logic here
    console.log('Login with:', this.loginEmail, this.loginPassword);
  }

  // Registration Function
  register() {
    // Implement your registration logic here
    console.log('Register with:', this.registerName, this.registerSurname, this.registerDOB, this.registerEmail);
  }
}
