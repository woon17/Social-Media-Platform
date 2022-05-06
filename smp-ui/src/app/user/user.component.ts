import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Post } from '../_class/post';
import { PostService } from '../_services/post.service';
import { UserAuthService } from '../_services/user-auth.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent implements OnInit {
  constructor(
    private userService: UserService,
    private postService: PostService,
    private router: Router,
    private userAuthService: UserAuthService
  ) {}
  public message: any;
  posts: Post[] | undefined;
  post: Post | undefined;
  ngOnInit(): void {
    this.forUser();
  }

  forUser() {
    this.userService.forUser().subscribe((data: Post[]) => {
      console.log(data);
      this.message = data;
      this.posts = data;
    });
  }

  updatePost(id: number | undefined) {
    this.router.navigate(['update-post', id]);
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
  deletePost(id: number | undefined) {
    this.postService.deletePostById(id).subscribe(() => {
      this.post = undefined;
      console.log('post deleted');
    });
  }

}
