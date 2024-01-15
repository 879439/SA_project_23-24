import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from '../app.component';
import { HttpClientModule } from '@angular/common/http';
import { UserService } from './user.service';



@NgModule({
  declarations: [
  ],
  imports: [
    BrowserModule,
    CommonModule,
    FormsModule, 
    HttpClientModule
  ],
  providers:[UserService]
})
export class UserModule { }
