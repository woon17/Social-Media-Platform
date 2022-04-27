import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
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
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  //
  public login(loginData: any) {
    console.log(loginData.value);
    console.log(`${this.baseUrl}/signin`);
    return this.httpclient.post(`${this.baseUrl}/signin`, loginData, {
      headers: this.requestHeader,
    });
  }

  public register(user:any): Observable<any>{
    return this.httpclient.post(`${this.baseUrl}/signup`, {
      userName: user.userName,
      userFirstName: user.firstName,
      userLastName: user.lastName,
      userPassword: user.userPassword
    }, {headers: this.requestHeader});
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
