import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserAuthService } from '../_services/user-auth.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  public userName = '';
  public userPassword = '';
  public message='';
  constructor(
    private userservice: UserService,
    private userAuthService: UserAuthService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  login(loginForm: NgForm) {
    console.log('form is submitted');
    console.log(loginForm.value);
    console.log("userName: " + this.userName);
    console.log("userName: " + this.userPassword);
    this.userservice.login(loginForm.value).subscribe(
      (response: any) => {
        console.log('---------------');
        console.log(loginForm.value);
        console.log(response);
        console.log(response.jwtToken);
        console.log(response.user.role[0].roleName);
        this.userAuthService.setRoles(response.user.role);
        this.userAuthService.setToken(response.jwtToken);
        const role = response.user.role[0].roleName;
        if (role === 'Admin') {
          this.router.navigate(['/admin']);
        }else if(role === 'User'){
          this.router.navigate(['/user']);
        }
      },
      (error) => {
        this.message=error.error.message;
        console.log(error);
      }
    );
  }

  goToRegistraton(){
    console.log("hello registration");
    this.router.navigate(['/registration']);

  }
}
