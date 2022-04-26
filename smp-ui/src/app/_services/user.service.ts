import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserAuthService } from './user-auth.service';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private baseUrl = 'http://localhost:8081';
  constructor(
    private httpclient: HttpClient,
    private userAuthService: UserAuthService
  ) {}

  requestHeader = new HttpHeaders({ 'no-auth': 'True' });

  //
  public login(loginData: any) {
    console.log(loginData.value);
    console.log(`${this.baseUrl}/authenticate`);
    return this.httpclient.post(`${this.baseUrl}/authenticate`, loginData, {
      headers: this.requestHeader,
    });
  }

  public forUser() {
    return this.httpclient.get(`${this.baseUrl}/forUser`, {
      responseType: 'text',
    });
  }

  public forAdmin() {
    return this.httpclient.get(`${this.baseUrl}/forAdmin`, {
      responseType: 'text',
    });
  }

  public roleMatch(allowedRoles: String[]): any {
    let isMatch = false;
    const userRoles: any = this.userAuthService.getRoles();

    if (userRoles != null && userRoles) {
      for (let i = 0; i < userRoles.length; i++) {
        for (let j = 0; j < allowedRoles.length; j++) {
          if (userRoles[i].roleName === allowedRoles[j]) {
            isMatch = true;
            return isMatch;
          } else {
            return isMatch;
          }
        }
      }
    }
  }
}
