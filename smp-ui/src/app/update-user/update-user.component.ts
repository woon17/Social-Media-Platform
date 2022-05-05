import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UpdateUser } from '../_class/updateUser';
import { User } from '../_class/user';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css'],
})
export class UpdateUserComponent implements OnInit {
  user: UpdateUser = new UpdateUser();
  userName!: string;
  uerRole = '';
  form: any = {};

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.userName = this.route.snapshot.params['userName'];
    this.userService.getUserByUserName(this.userName).subscribe((data) => {
      this.uerRole = data.role[0].roleName;
      this.user = data;
      this.user.userPassword = '';
      this.form.firstName = data.userFirstName;
      this.form.lastName = data.userLastName;
    });
  }

  onSubmit() {
    console.log("update to user: " + JSON.stringify(this.user));
    this.user.userFirstName=this.form.firstName;
    this.user.userLastName=this.form.lastName;
    this.user.userPassword=this.form.userPassword;
    this.userService.updateUser(this.userName, this.user).subscribe((data) => {
      this.goToUsersList();
    });
  }

  goToUsersList() {
    this.router.navigate(['/admin']);
  }

}
