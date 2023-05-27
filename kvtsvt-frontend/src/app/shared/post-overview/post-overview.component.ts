import { PostModel } from './../../post/post-model';
import { Component, OnInit, ViewEncapsulation, Input } from '@angular/core';
import { faComments } from '@fortawesome/free-solid-svg-icons';
import { Router } from '@angular/router';
import { GroupService } from 'src/app/group/group.service';
import { PostService } from 'src/app/post/post.service';
import { AuthService } from 'src/app/auth/shared/auth.service';
import { throwError } from 'rxjs';
import { RegisterRequestModel } from 'src/app/auth/register/register-request-model';
import { GroupModel } from 'src/app/group/group-model';

@Component({
  selector: 'app-post-overview',
  templateUrl: './post-overview.component.html',
  styleUrls: ['./post-overview.component.css'],
})
export class PostOverviewComponent implements OnInit {
  faComments = faComments;
  @Input() posts: PostModel[] = [];

  user!: RegisterRequestModel;
  loggedUserId: number = 0;
  group!: GroupModel;
  post!: PostModel;

  constructor(
    private router: Router,
    private groupService: GroupService,
    private postService: PostService,
    private authService: AuthService
  ) {
    this.user = {
      id: 0,
      firstname: '',
      lastname: '',
      username: '',
      email: '',
      password: '',
    };
    this.group = {
      id: 0,
      adminId: 0,
    };
  }

  ngOnInit(): void {}

  goToPost(id: number): void {
    this.router.navigateByUrl('/view-post/' + id);
  }

  getUser(id: number) {
    this.authService.getUser(id).subscribe((data) => {
      this.user = data;
    });
  }

  getGroup(id: number) {
    this.groupService.getGroup(id).subscribe((data) => {
      this.group = data;
    });
  }
}
