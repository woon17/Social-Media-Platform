import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { map, Observable } from 'rxjs';
import { PostService } from '../_services/post.service';
import { UserAuthService } from '../_services/user-auth.service';
import { UserService } from '../_services/user.service';

@Injectable({
  providedIn: 'root',
})
export class MatchPostUserOrRoleGuard implements CanActivate {
  constructor(
    private userAuthService: UserAuthService,
    private router: Router,
    private postService: PostService,
    private userService: UserService
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    const postId = route.paramMap.get('id');
    var post: any;
    console.log('IsMatchedPostUserGuard postId: ' + postId);

    if (this.userAuthService.getToken() !== null) {
      const role = route.data['roles'] as Array<string>;
      console.log('role array: ' + role);

      if (role) {
        const match = this.userService.roleMatch(role);
        console.log('match: ' + match);
        if (match) {
          return true;
        }
      }
    }

    return this.postService.getPostById(Number(postId)).pipe(
      map((data) => {
        try {
          if (this.userAuthService.getJwtSub() === data.user?.userName) {
            console.log('same user name');
            return true;
          } else {
            console.log('different user name');
            this.router.navigate(['/forbidden']);
            return false;
          }
        } catch (e) {
          // post is not existing
          console.log('different user name, catch block');
          this.router.navigate(['/feeds']);
          return false;
        }
      })
    );
  }
}
