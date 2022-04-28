import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../_class/post';
import { AppSettings } from '../_help/appSettings';

@Injectable({
  providedIn: 'root',
})
export class PostService {

  constructor(private httpclient: HttpClient) {}

  getAllPosts() : Observable<Post[]>{
    console.log(`${AppSettings.API_ENDPOINT}/getAllPosts`);
    return this.httpclient.get<Post[]>(`${AppSettings.API_ENDPOINT}/getAllPosts`, {
      headers: AppSettings.NO_AUTH_HEADER,
    });
  }
  deletePostById(id: number | undefined) {
    console.log(`${AppSettings.API_ENDPOINT}}/deletePost/${id}`);
    return this.httpclient.delete(`${AppSettings.API_ENDPOINT}/deletePost/${id}`);
  }
}
