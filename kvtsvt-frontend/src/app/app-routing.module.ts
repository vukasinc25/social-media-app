import { ViewPostComponent } from './post/view-post/view-post.component';
import { AllGroupsComponent } from './group/all-groups/all-groups.component';
import { CreateGroupComponent } from './group/create-group/create-group.component';
import { CreatePostComponent } from './post/create-post/create-post.component';
import { HomeComponent } from './home/home.component';
import { NgModule, Component } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './auth/register/register.component';
import { LoginComponent } from './auth/login/login.component';
import { ChangePasswordComponent } from './auth/change-password/change-password.component';
import { UserProfileComponent } from './auth/user-profile/user-profile.component';
import { EditGroupComponent } from './group/edit-group/edit-group.component';
import { EditPostComponent } from './post/edit-post/edit-post.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'create-group', component: CreateGroupComponent },
  { path: 'edit-group', component: EditGroupComponent },
  { path: 'all-groups', component: AllGroupsComponent },
  { path: 'create-post', component: CreatePostComponent },
  { path: 'view-post/:id', component: ViewPostComponent },
  { path: 'change-password', component: ChangePasswordComponent },
  { path: 'user-profile/:name', component: UserProfileComponent },
  { path: 'edit-post/:postId', component: EditPostComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
