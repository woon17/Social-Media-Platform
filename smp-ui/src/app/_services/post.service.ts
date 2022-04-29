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


  getPostById(id: number): Observable<Post> {
    console.log(`${AppSettings.API_ENDPOINT}/getPostById/${id}`);
    return this.httpclient.get<Post>(
      `${AppSettings.API_ENDPOINT}/getPostById/${id}`
    );
  }

  public createPost(post: any): Observable<any> {
    console.log("createPost(post: any): " + post);
    return this.httpclient.post(
      `${AppSettings.API_ENDPOINT}/createPost`,
      {
        type: post.type,
        caption: post.caption,
        link: post.link,
        views: post.views,
      }
    );
  }

  updatePost(id: number, post: Post): Observable<Object> {
    console.log(`${AppSettings.API_ENDPOINT}/updatePost/${id}`);
    return this.httpclient.put(
      `${AppSettings.API_ENDPOINT}/updatePost/${id}`,
      post
    );
  }


  deletePostById(id: number | undefined) {
    console.log(`${AppSettings.API_ENDPOINT}}/deletePost/${id}`);
    return this.httpclient.delete(`${AppSettings.API_ENDPOINT}/deletePost/${id}`);
  }
}
