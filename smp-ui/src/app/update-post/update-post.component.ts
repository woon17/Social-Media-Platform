import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Post } from '../_class/post';
import { PostService } from '../_services/post.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-update-post',
  templateUrl: './update-post.component.html',
  styleUrls: ['./update-post.component.css']
})
export class UpdatePostComponent implements OnInit {
  post: Post = new Post();
  id!: number;
  uerRole='';
  constructor(
    private postService: PostService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.postService.getPostById(this.id).subscribe((data) => {
      this.post = data;
    });
  }

  onSubmit() {
    this.postService.updatePost(this.id, this.post).subscribe((data) => {
      this.goToFeeds();
    });
  }

  goToFeeds() {
    this.router.navigate(['/feeds']);
  }
}
