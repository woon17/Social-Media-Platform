import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../_class/user';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent implements OnInit {

  user: User = new User();
  userName!: string;
  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.userName = this.route.snapshot.params['userName'];
    this.userService.getUserByUserName(this.userName).subscribe((data) => {
      this.user = data;
    });
  }

  onSubmit() {
    this.userService.updateUser(this.userName, this.user).subscribe((data) => {
      this.goToUsersList();
    });
  }

  goToUsersList() {
    this.router.navigate(['/admin']);
  }

}
