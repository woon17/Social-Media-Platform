import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Post } from '../_class/post';
import { PostService } from '../_services/post.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {
  constructor(private postService: PostService, private router: Router) {}
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
      this.router.navigate(["update-post", id]);
    }

    deletePost(id: number | undefined) {
      this.postService.deletePostById(id).subscribe(() => {
        this.fetchPosts();
        this.post=undefined;
        console.log('post deleted');
      });
    }

}
