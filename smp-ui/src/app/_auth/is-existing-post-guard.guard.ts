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
export class IsExistingPostGuardGuard implements CanActivate {
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
    return this.postService.getPostById(Number(postId)).pipe(
      map((data) => {
        try {
          if (data.user?.userName!==null) {
            return true;
          } else {
            return false;
          }
        } catch (e) {
          this.router.navigate(['/feeds']);
          return false;
        }
      })
    );
  }
}
