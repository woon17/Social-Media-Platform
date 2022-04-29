import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../_class/post';
import { User } from '../_class/user';
import { AppSettings } from '../_help/appSettings';
import { UserAuthService } from './user-auth.service';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  userName = '';

  constructor(
    private httpclient: HttpClient,
    private userAuthService: UserAuthService
  ) {
    console.log("----------------------UserService constructor----------------------------");
  }

  public login(loginData: any) {
    console.log(loginData.value);
    console.log(`${AppSettings.API_ENDPOINT}/signin`);
    return this.httpclient.post(
      `${AppSettings.API_ENDPOINT}/signin`,
      loginData,
      {
        headers: AppSettings.NO_AUTH_HEADER,
      }
    );
  }

  public register(user: any): Observable<any> {
    return this.httpclient.post(
      `${AppSettings.API_ENDPOINT}/signup`,
      {
        userName: user.userName,
        userFirstName: user.firstName,
        userLastName: user.lastName,
        userPassword: user.userPassword,
      },
      { headers: AppSettings.NO_AUTH_HEADER }
    );
  }

  public forUser(): Observable<Post[]> {
    return this.httpclient.get<Post[]>(`${AppSettings.API_ENDPOINT}/forUser`);
  }

  public forAdmin(): Observable<User[]> {
    return this.httpclient.get<User[]>(`${AppSettings.API_ENDPOINT}/forAdmin`);
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
          }
        }
      }
      return false;
    }
  }

  updateUser(username: string, user: User): Observable<Object> {
    console.log(`${AppSettings.API_ENDPOINT}/updateUser/${username}`);
    return this.httpclient.put(
      `${AppSettings.API_ENDPOINT}/updateUser/${username}`,
      user
    );
  }

  getUserByUserName(userName: string): Observable<User> {
    console.log(`${AppSettings.API_ENDPOINT}/getUser/${userName}`);
    return this.httpclient.get<User>(
      `${AppSettings.API_ENDPOINT}/getUser/${userName}`
    );
  }

  deleteUserByUserName(userName: string | undefined) {
    console.log(`${AppSettings.API_ENDPOINT}}/deleteUser/${userName}`);
    return this.httpclient.delete(
      `${AppSettings.API_ENDPOINT}/deleteUser/${userName}`
    );
  }
}
