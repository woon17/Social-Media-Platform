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
  type: string = 'video';
  constructor(private postService: PostService, private router: Router) {}

  ngOnInit(): void {}

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

  sleep(ms:any) {
    return new Promise((resolve) => {
      setTimeout(resolve, ms);
    });
  }

  create(registerForm: NgForm) {
    console.log('form is submitted');
    console.log(this.form);
    if(this.form.type === 'image' || this.form.type === 'video'){
      this.postService.createPostWithFile(this.form, this.media).subscribe(async () => {
        await this.sleep(500);
        this.router.navigate(['/feeds']);
      });
    }else{// hyperlink type new pos
      this.postService.createPostWithHyperlink(this.form).subscribe(async () => {
        await this.sleep(500);
        this.router.navigate(['/feeds']);
      });
    }


  }
}
