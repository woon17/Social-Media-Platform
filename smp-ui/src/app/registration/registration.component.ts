import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserAuthService } from '../_services/user-auth.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
})
export class RegistrationComponent implements OnInit {
  public userName = '';
  public firstName = '';
  public lastName = '';
  public userPassword = '';
  public message = '';
  constructor(private userservice: UserService, private router: Router) {}

  ngOnInit(): void {}
  register(registerForm: NgForm) {
    console.log('form is submitted');
    console.log(registerForm.value);
    console.log('userName: ' + this.userName);
    console.log('userName: ' + this.userPassword);
    this.userservice.login(registerForm.value).subscribe(
      (response: any) => {
        console.log('---------------');
        console.log(registerForm.value);
        console.log(response.jwtToken);
        console.log(response.user.role[0].roleName);
        const role = response.user.role[0].roleName;
        this.router.navigate(['/admin']);
      },
      (error) => {
        this.message =
          'Bad credentials, please enter valid user name and password';
        console.log("+++++++++++++++++++++");
        console.log(error);
      }
    );
  }
}
