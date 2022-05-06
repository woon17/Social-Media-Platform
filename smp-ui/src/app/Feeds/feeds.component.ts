import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Post } from '../_class/post';
import { PostService } from '../_services/post.service';
import { UserAuthService } from '../_services/user-auth.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-feeds',
  templateUrl: './feeds.component.html',
  styleUrls: ['./feeds.component.css'],
})
export class FeedsComponent implements OnInit {
  currentPost: Post|undefined;
  currentIndex = -1;
  title = '';
  page = 1;
  count = 0;
  pageSize = 10;
  pageSizes = [10, 20, 30];



  constructor(
    private postService: PostService,
    private router: Router,
    private userService: UserService,
    private userAuthService: UserAuthService
  ) {}
  public message: any;
  posts: Post[] = [];
  post: Post | undefined;

  ngOnInit(): void {
    // this.fetchPosts();
    console.log("---------feeds component call ngOnInit");
    this.retrievePosts();
  }



  fetchPosts() {
    this.postService.getAllPosts().subscribe((data: Post[]) => {
      console.log(data);
      this.posts = data;
    });
  }

  // 2. do routing,
  updatePost(id: number | undefined) {
    this.router.navigate(['update-post', id]);
  }

  deletePost(id: number | undefined) {
    this.postService.deletePostById(id).subscribe(() => {
      this.fetchPosts();
      this.post = undefined;
      console.log('post deleted');
    });
  }
  public matchRole(role: any) {
    return this.userService.roleMatch(role);
  }

  getUserName() {
    return this.userAuthService.getJwtSub();
  }

  createPost() {
    // if(this.matchRole(["Admin"]) || this.matchRole(["User"])){
    if (this.matchRole(['User'])) {
      this.router.navigate(['/create-post']);
    } else if (this.matchRole(['Admin'])) {
      this.router.navigate(['/forbidden']);
    } else {
      this.router.navigate(['/login']);
    }
  }

  // TODO: use click view api to update view number
  increaseView(postid: any) {
    console.log('increase view for post_' + postid);
    this.postService.increaseView(postid).subscribe(() => {
      this.retrievePosts();
      this.post = undefined;
      console.log('post deleted');
    });
  }


  getRequestParams(searchTitle: string, page: number, pageSize: number): any {
    let params: any = {};
    if (searchTitle) {
      params[`title`] = searchTitle;
    }
    if (page) {
      params[`page`] = page - 1;
    }
    if (pageSize) {
      params[`size`] = pageSize;
    }
    return params;
  }

  retrievePosts(): void {
    const params = this.getRequestParams(this.title, this.page, this.pageSize);
    this.postService.getAll(params)
    .subscribe(
      response => {
        const { posts, totalItems } = response;
        this.posts = posts;
        this.count = totalItems;
        console.log(response);
      });
  }
  handlePageChange(event: number): void {
    this.page = event;
    this.retrievePosts();
  }
  handlePageSizeChange(event: any): void {
    this.pageSize = event.target.value;
    this.page = 1;
    this.retrievePosts();
  }

  setActiveTutorial(post: Post, index: number): void {
    this.currentPost = post;
    this.currentIndex = index;
  }

}
