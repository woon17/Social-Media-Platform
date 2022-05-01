import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { PostService } from '../_services/post.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css'],
})
export class CreatePostComponent implements OnInit {
  form: any = {};
  media?: File;
  constructor(private postService: PostService, private router: Router) {}

  ngOnInit(): void {}

  onImageSelected($event: any) {
    if ($event.target.files && $event.target.files[0]) {
      let file = $event.target.files[0];
      console.log(file);
      if (file.type == "image/jpeg" || file.type == "image/png") {
        this.media = file;
        // this.hasMedia = true;
        console.log("correct");
      } else {
        console.log("wrong");
        // //call validation
        // this.registerForm.reset();
        // this.registerForm.controls["imageInput"].setValidators([Validators.required]);
        // this.registerForm.get('imageInput').updateValueAndValidity();
      }
    }
  }



  create(registerForm: NgForm) {
    console.log('form is submitted');
    console.log(registerForm);

    this.postService.createPost(this.form, this.media).subscribe(
      (response: any) => {
        this.router.navigate(['/feeds']);
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
