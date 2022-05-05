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
  media?: File;

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
    console.log("media: " + this.media);
    console.log(this.media === undefined);
    if(this.media === undefined){
      this.postService.updatePost(this.id, this.post).subscribe((data) => {
        this.goToFeeds();
      });
    }else{
      console.log("1");
      this.postService.updatePostWithFile(this.id, this.post, this.media).subscribe(async () => {
        await this.sleep(500);
        this.goToFeeds();
      });
    }

  }
  sleep(ms:any) {
    return new Promise((resolve) => {
      setTimeout(resolve, ms);
    });
  }
  goToFeeds() {
    this.router.navigate(['/feeds']);
  }
  onFileSelected($event: any) {
    if ($event.target.files && $event.target.files[0]) {
      let file = $event.target.files[0];
      console.log(file);
      if (file.type == 'image/jpeg' || file.type == 'image/png') {
        this.media = file;
        // this.hasMedia = true;
        console.log('imge media');
      } else {
        this.media = file;

        console.log('video media');
        // //call validation
        // this.registerForm.reset();
        // this.registerForm.controls["imageInput"].setValidators([Validators.required]);
        // this.registerForm.get('imageInput').updateValueAndValidity();
      }
    }
  }
}
