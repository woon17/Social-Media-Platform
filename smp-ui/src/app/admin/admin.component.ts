import { Component, OnInit } from '@angular/core';
import { User } from '../_help/user';
import { UserService } from '../_services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
})
export class AdminComponent implements OnInit {
  constructor(private userService: UserService, private router: Router) {}
  public message: any;
  users: User[] | undefined;
  user: User | undefined;

  ngOnInit(): void {
    this.fetchUsers();
  }

  fetchUsers() {
    this.userService.forAdmin().subscribe((data: User[]) => {
      console.log(data);
      this.users = data;
    });
  }

    // 2. do routing,
    updateUser(userName: string | undefined) {
      this.router.navigate(["update-user", userName]);
    }

    deleteUser(userName: string | undefined) {
      this.userService.deleteUserByUserName(userName).subscribe(() => {
        this.fetchUsers();
        this.user=undefined;
        console.log('user deleted');
      });
    }
}
