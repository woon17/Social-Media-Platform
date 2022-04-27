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
  form: any = {};
  message = '';
  constructor(private userservice: UserService, private router: Router) {}

  ngOnInit(): void {}
  register(registerForm: NgForm) {
    console.log('form is submitted');
    console.log(registerForm);
    this.userservice.register(this.form).subscribe(
      (response: any) => {
        this.router.navigate(['/signin']);
      },
      (error) => {
        this.message = error.error.message;
        console.log(error)
      }
    );
  }
}
