import { Injectable } from '@angular/core';
import { JwtHelperService, JWT_OPTIONS    } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root',
})
export class UserAuthService {
  constructor(private jwtHelperService:JwtHelperService) {}

  public setRoles(roles: []) {
    localStorage.setItem('roles', JSON.stringify(roles));
  }

  public getRoles() {
    return JSON.parse(localStorage.getItem('roles')!);
  }

  public setToken(jwtToken: string) {
    localStorage.setItem('jwtToken', jwtToken);
  }

 public getToken(): any {
    return localStorage.getItem('jwtToken');
  }

  public clear(){
    console.log("clear()");
    localStorage.clear();
  }

  public isLoggedIn(){
        return this.getRoles() && this.getToken();
  }

  public getJwtSub():string{
    return this.jwtHelperService.decodeToken(this.getToken())?.sub;
  }
}
