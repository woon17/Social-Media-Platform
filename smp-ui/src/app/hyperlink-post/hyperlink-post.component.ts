import { Component, ElementRef, Input, OnInit } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { Post } from '../_class/post';
import { PostService } from '../_services/post.service';
import { UserAuthService } from '../_services/user-auth.service';
import { UserService } from '../_services/user.service';
@Component({
  selector: 'app-hyperlink-post',
  templateUrl: './hyperlink-post.component.html',
  styleUrls: ['./hyperlink-post.component.css'],
})
export class HyperlinkPostComponent implements OnInit {
  @Input() post!: Post;

  link!: SafeResourceUrl;
  constructor(
    private postService: PostService,
    private router: Router,
    private userService: UserService,
    private userAuthService: UserAuthService,
    private hostElement: ElementRef,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    // this.refreshPost();
    // const iframe = this.hostElement.nativeElement.querySelector('iframe');
    // iframe.src = this.post.link;
    this.link =
      this.sanitizer.bypassSecurityTrustResourceUrl(this.post.link);
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
      this.router.navigate(['/feeds']);
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
}
