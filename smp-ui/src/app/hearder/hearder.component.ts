import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserAuthService } from '../_services/user-auth.service';
import { UserService } from '../_services/user.service';
import { JwtHelperService } from '@auth0/angular-jwt';
@Component({
  selector: 'app-hearder',
  templateUrl: './hearder.component.html',
  styleUrls: ['./hearder.component.css'],
})
export class HearderComponent implements OnInit {
  constructor(
    private userAuthService: UserAuthService,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit(): void {}

  public isLoggedIn() {
    return this.userAuthService.isLoggedIn();
  }
  public logout() {
    console.log('call logout()');
    this.userAuthService.clear();
    this.router.navigate(['/login']);
  }

  getUserName() {
    return this.userAuthService.getJwtSub();
  }

  public matchRole(role: any) {
    return this.userService.roleMatch(role);
  }
}
