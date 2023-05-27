import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { ReactiveFormsModule } from '@angular/forms';
import { RegisterComponent } from './auth/register/register.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { LoginComponent } from './auth/login/login.component';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
// import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { EditorModule } from '@tinymce/tinymce-angular';
import { TokenInterceptor } from './auth/shared/token-interceptor';
import { HomeComponent } from './home/home.component';
import { PostOverviewComponent } from './shared/post-overview/post-overview.component';
import { SidebarComponent } from './shared/sidebar/sidebar.component';
import { GroupSidebarComponent } from './shared/group-sidebar/group-sidebar.component';
import { ReactionButtonComponent } from './shared/reaction-button/reaction-button.component';
import { CreateGroupComponent } from './group/create-group/create-group.component';
import { CreatePostComponent } from './post/create-post/create-post.component';
import { AllGroupsComponent } from './group/all-groups/all-groups.component';
import { ViewPostComponent } from './post/view-post/view-post.component';
import { ChangePasswordComponent } from './auth/change-password/change-password.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UserProfileComponent } from './auth/user-profile/user-profile.component';
import { EditGroupComponent } from './group/edit-group/edit-group.component';
import { EditPostComponent } from './post/edit-post/edit-post.component';
import { ViewGroupComponent } from './group/view-group/view-group.component';
import { ViewGroupSidebarComponent } from './shared/view-group-sidebar/view-group-sidebar.component';
import { ViewCommentComponent } from './comment/view-comment/view-comment.component';
import { EditCommentComponent } from './comment/edit-comment/edit-comment.component';
import { EditUserComponent } from './auth/edit-user/edit-user.component';
import { AdminGroupComponent } from './group/admin-group/admin-group.component';
import { AdminPageComponent } from './auth/admin-page/admin-page.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    RegisterComponent,
    LoginComponent,
    HomeComponent,
    PostOverviewComponent,
    SidebarComponent,
    GroupSidebarComponent,
    ReactionButtonComponent,
    CreateGroupComponent,
    CreatePostComponent,
    AllGroupsComponent,
    ViewPostComponent,
    ChangePasswordComponent,
    UserProfileComponent,
    EditGroupComponent,
    EditPostComponent,
    ViewGroupComponent,
    ViewGroupSidebarComponent,
    ViewCommentComponent,
    EditCommentComponent,
    EditUserComponent,
    AdminGroupComponent,
    AdminPageComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxWebstorageModule.forRoot(),
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    FontAwesomeModule,
    EditorModule,
    NgbModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
