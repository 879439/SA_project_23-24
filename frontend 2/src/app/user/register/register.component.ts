import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { error } from 'console';
import { UserModule } from '../user.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-register',
  standalone:true,
  imports: [FormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent implements OnInit {
  email:string="";
  username:string="";
  password:string="";
  sex:string="";
  firstname:string="";
  lastname:string="";
  birthday:string="";
  roles:string[]=[];

  constructor(private userService: UserService){}
  ngOnInit(): void {
  }

  register(){
    this.roles.push("user");
    this.userService.register(this.username, this.email, this.password, this.lastname, this.firstname, this.sex, this.birthday, this.roles).subscribe({
      next: (data) => {
        console.log(data);
      },
      error: (error) => {
        console.error(error);
      },
      complete: () => {
        console.log('Operazione di registrazione completata');
      }
    });
  }
}
