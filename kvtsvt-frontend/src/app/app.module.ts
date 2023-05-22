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
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
