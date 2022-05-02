import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { UserAuthService } from '../_services/user-auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(
    private userAuthService: UserAuthService,
    private router: Router
  ) {}
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    console.log("req:: "+JSON.stringify(req));
    console.log(req.headers.get('No-Auth'));
    console.log(req.headers.get('No-Auth') === 'True');
    if (req.headers.get('No-Auth') === 'True') {
      return next.handle(req.clone());
    }
    const token = this.userAuthService.getToken();
    req = this.addToken(req, token);
    console.log("req addToken:: "+JSON.stringify(req));

    return next.handle(req).pipe(
      catchError((err: HttpErrorResponse) => {
        console.log(err.status);
        if (err.status === 401) { // user is not logged in
          this.router.navigate(['/login']);
        } else if (err.status === 403) {// forbidden
          this.router.navigate(['/forbidden']);
        }
        return throwError(() => new Error('Some thing is wrong'));
      })
    );
  }

  private addToken(request: HttpRequest<any>, token: string) {
    return request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
  }
}
