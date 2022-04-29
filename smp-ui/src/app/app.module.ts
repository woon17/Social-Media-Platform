import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AdminComponent } from './admin/admin.component';
import { UserComponent } from './user/user.component';
import { LoginComponent } from './login/login.component';
import { HearderComponent } from './hearder/hearder.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { AuthGuard } from './_auth/auth.guard';
import { UserService } from './_services/user.service';
import { AuthInterceptor } from './_auth/auth.interceptor';
import { RegistrationComponent } from './registration/registration.component';
import { UpdateUserComponent } from './update-user/update-user.component';
import { PostComponent } from './post/post.component';
import { FeedsComponent } from './Feeds/feeds.component';
import { PicturePostComponent } from './picture-post/picture-post.component';
import { VideoPostComponent } from './video-post/video-post.component';
import { TextPostComponent } from './text-post/text-post.component';
import { JwtHelperService, JWT_OPTIONS } from '@auth0/angular-jwt';
import { UpdatePostComponent } from './update-post/update-post.component';

@NgModule({
  declarations: [
    AppComponent,
    FeedsComponent,
    AdminComponent,
    UserComponent,
    LoginComponent,
    HearderComponent,
    ForbiddenComponent,
    RegistrationComponent,
    UpdateUserComponent,
    PostComponent,
    PicturePostComponent,
    VideoPostComponent,
    TextPostComponent,
    UpdatePostComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule, // used for login form
    HttpClientModule, // add for userService
    RouterModule, // used for user-auth service
  ],
  providers: [
    AuthGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
    UserService,
    { provide: JWT_OPTIONS, useValue: JWT_OPTIONS },
    JwtHelperService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
