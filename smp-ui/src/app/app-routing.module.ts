import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { CreatePostComponent } from './create-post/create-post.component';
import { FeedsComponent } from './Feeds/feeds.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { LoginComponent } from './login/login.component';
import { PostComponent } from './post/post.component';
import { RegistrationComponent } from './registration/registration.component';
import { UpdatePostComponent } from './update-post/update-post.component';
import { UpdateUserComponent } from './update-user/update-user.component';
import { UserComponent } from './user/user.component';
import { AuthGuard } from './_auth/auth.guard';
import { IsExistingPostGuardGuard } from './_auth/is-existing-post-guard.guard';
import { IsMatchedPostUserGuard } from './_auth/is-matched-post-user.guard';
import { IsNotSignInGuard } from './_auth/is-not-sign-in.guard';

const routes: Routes = [
  { path: 'feeds', component: FeedsComponent },
  { path: '', component: FeedsComponent },
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [AuthGuard],
    data: { roles: ['Admin'] },
  },
  // { path: 'post', component: PostComponent },
  {
    path: 'update-post/:id',
    component: UpdatePostComponent,
    canActivate: [IsMatchedPostUserGuard, IsExistingPostGuardGuard],
    data: { roles: ['Admin']},
  }, // add update a post
  {
    path: 'create-post',
    component: CreatePostComponent,
    canActivate: [AuthGuard],
    data: { roles: ['User'] },
  }, // add update a post
  {
    path: 'update-user/:userName',
    component: UpdateUserComponent,
    canActivate: [AuthGuard],
    data: { roles: ['Admin'] },
  }, // add update a user
  {
    path: 'user',
    component: UserComponent,
    canActivate: [AuthGuard],
    data: { roles: ['User'] },
  },
  { path: 'login', component: LoginComponent, canActivate: [IsNotSignInGuard] }, // no need to use auth guard
  {
    path: 'registration',
    component: RegistrationComponent,
    canActivate: [IsNotSignInGuard],
  }, // for register a new user
  { path: 'forbidden', component: ForbiddenComponent }, // no need to use auth guard
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
