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
  constructor(
    private postService: PostService,
    private router: Router,
    private userService: UserService,
    private userAuthService: UserAuthService
  ) {}
  public message: any;
  posts: Post[] | undefined;
  post: Post | undefined;

  ngOnInit(): void {
    this.fetchPosts();
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
    } else {
      this.router.navigate(['/login']);
    }
  }
}
