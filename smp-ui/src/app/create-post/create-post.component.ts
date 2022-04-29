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

  constructor(private postService: PostService, private router:Router) {}

  ngOnInit(): void {}

  create(registerForm: NgForm) {
    console.log('form is submitted');
    console.log(registerForm);
    this.postService.createPost(this.form).subscribe(
      (response: any) => {
        this.router.navigate(['/feeds']);
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
