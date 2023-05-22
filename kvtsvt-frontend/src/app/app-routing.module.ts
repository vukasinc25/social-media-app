import { ViewPostComponent } from './post/view-post/view-post.component';
import { AllGroupsComponent } from './group/all-groups/all-groups.component';
import { CreateGroupComponent } from './group/create-group/create-group.component';
import { CreatePostComponent } from './post/create-post/create-post';
import { HomeComponent } from './home/home.component';
import { NgModule, Component } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './auth/register/register.component';
import { LoginComponent } from './auth/login/login.component';

const routes: Routes = [
  {path: '', component: HomeComponent}
  {path: 'register', component: RegisterComponent},
  {path: 'login', component: LoginComponent}
  {path: 'create-post', component: CreatePostComponent}
  {path: 'create-group', component: CreateGroupComponent}
  {path: 'all-groups', component: AllGroupsComponent}
  {path: 'view-post/:id', component: ViewPostComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
