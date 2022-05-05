import {
  HttpClient,
  HttpEvent,
  HttpParams,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../_class/post';
import { AppSettings } from '../_help/appSettings';

@Injectable({
  providedIn: 'root',
})
export class PostService {


  constructor(private httpclient: HttpClient) {}

  getAllPosts(): Observable<Post[]> {
    console.log(`${AppSettings.API_ENDPOINT}/getAllPosts`);
    return this.httpclient.get<Post[]>(
      `${AppSettings.API_ENDPOINT}/getAllPosts`
    );
  }

  getPostById(id: any): Observable<Post> {
    console.log(`${AppSettings.API_ENDPOINT}/getPostById/${id}`);
    return this.httpclient.get<Post>(
      `${AppSettings.API_ENDPOINT}/getPostById/${id}`
    );
  }

  // with uploading a file (image or video)
  public createPostWithFile(post: any, media: any): Observable<any> {
    const formData: FormData = new FormData();
    // formData.append('post',  JSON.stringify(postObj));
    formData.append('file', media);
    formData.append('type', post.type);
    formData.append('caption', post.caption);

    const req = new HttpRequest(
      'POST',
      `${AppSettings.API_ENDPOINT}/createPostWithFile`,
      formData,
      {
        responseType: 'json',
      }
    );
    return this.httpclient.request(req);
  }

  public createPostWithHyperlink(post: any): Observable<any> {
    console.log(`${AppSettings.API_ENDPOINT}/createPostWithHyperlink/${post}`);
    return this.httpclient.post(
      `${AppSettings.API_ENDPOINT}/createPostWithHyperlink`,
      post
    );
  }


  updatePost(id: number, post: Post): Observable<Object> {
    console.log(`${AppSettings.API_ENDPOINT}/updatePost/${id}`);

    return this.httpclient.put(
      `${AppSettings.API_ENDPOINT}/updatePost/${id}`,
      post
    );
  }

  updatePostWithFile(id: number, post: any, media: File) {
    console.log("2");
    const formData: FormData = new FormData();
    // formData.append('post',  JSON.stringify(postObj));
    formData.append('file', media);
    formData.append('type', post.type);
    formData.append('caption', post.caption);
    const req = new HttpRequest(
      'POST',
      `${AppSettings.API_ENDPOINT}/updatePostWithFile/${id}`,
      formData,
      {
        responseType: 'json',
      }
    );
    return this.httpclient.request(req);
  }

  deletePostById(id: number | undefined) {
    console.log(`${AppSettings.API_ENDPOINT}}/deletePost/${id}`);
    return this.httpclient.delete(
      `${AppSettings.API_ENDPOINT}/deletePost/${id}`
    );
  }

  increaseView(id: number | undefined) {
    console.log(`${AppSettings.API_ENDPOINT}}/addViewsCount/${id}`);
    return this.httpclient.put(
      `${AppSettings.API_ENDPOINT}/addViewsCount/${id}`,
      {}
    );
  }
  getAll(params: any): Observable<any> {

    return this.httpclient.get<any>(`${AppSettings.API_ENDPOINT}/getAllPosts`, { params });
    // const req = new HttpRequest(
    //   'get',
    //   `${AppSettings.API_ENDPOINT}/getAllPosts`,
    //   { params },
    //   {
    //     headers: AppSettings.NO_AUTH_HEADER,
    //   }
    // );

    // return this.httpclient.request(req);
  }
}
