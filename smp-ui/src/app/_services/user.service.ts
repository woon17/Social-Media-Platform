import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private baseUrl = 'http://localhost:8081';
  constructor(private httpclient: HttpClient) {}

  requestHeader = new HttpHeaders({ 'no-auth': 'True' });

  //
  public login(loginData: any) {
    console.log(loginData.value);
    console.log(`${this.baseUrl}/authenticate`);
    return this.httpclient.post(`${this.baseUrl}/authenticate`, loginData, {
      headers: this.requestHeader,
    });
  }
}
