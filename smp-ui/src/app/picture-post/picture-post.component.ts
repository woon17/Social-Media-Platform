import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Post } from '../_class/post';
import { PostService } from '../_services/post.service';
import { UserAuthService } from '../_services/user-auth.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-picture-post',
  templateUrl: './picture-post.component.html',
  styleUrls: ['./picture-post.component.css'],
})
export class PicturePostComponent implements OnInit {
  @Input() post: any;
  showMedia=false;

  constructor(
    private postService: PostService,
    private router: Router,
    private userService: UserService,
    private userAuthService: UserAuthService
  ) {}

  ngOnInit(): void {
  }
  refreshPost() {
    this.postService.getPostById(this.post.id).subscribe((data) => {
      this.post = data;
    });
  }
  // 2. do routing,
  updatePost(id: number | undefined) {
    this.router.navigate(['update-post', id]);
  }

  deletePost(id: number | undefined) {
    this.postService.deletePostById(id).subscribe(() => {
      // this.post = undefined;
      console.log('post deleted');
      // this.router.navigate(['/feeds']);
      this.post=null;

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
      // this.post = undefined;
      console.log('view +1');
      this.refreshPost();
    });
  }
  toggleMediaView(){
    this.showMedia=!this.showMedia;
    if(this.showMedia === true){
      this.increaseView(this.post.id);
    }
  }
}
