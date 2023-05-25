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
import { ViewGroupComponent } from './group/view-group/view-group.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  //user
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'user-profile/:id', component: UserProfileComponent },
  { path: 'change-password', component: ChangePasswordComponent },
  //group
  { path: 'create-group', component: CreateGroupComponent },
  { path: 'group/:id', component: ViewGroupComponent },
  { path: 'edit-group/:id', component: EditGroupComponent },
  { path: 'all-groups', component: AllGroupsComponent },
  //post
  { path: 'create-post', component: CreatePostComponent },
  { path: 'view-post/:id', component: ViewPostComponent },
  { path: 'edit-post/:id', component: EditPostComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
