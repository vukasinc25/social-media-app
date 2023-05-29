import { ActivatedRoute, Router } from '@angular/router';
import { PostModel } from 'src/app/post/post-model';
import { GroupModel } from './../group-model';
import { GroupService } from './../group.service';
import { Component, OnInit } from '@angular/core';
import { throwError } from 'rxjs';
import { PostService } from 'src/app/post/post.service';
import { GroupRequestModel } from '../group-request-model';
import { GroupRequestService } from '../group-request.service';
import { AuthService } from 'src/app/auth/shared/auth.service';

@Component({
  selector: 'app-view-group',
  templateUrl: './view-group.component.html',
  styleUrls: ['./view-group.component.css'],
})
export class ViewGroupComponent implements OnInit {
  posts: Array<PostModel> = [];
  groupRequests: Array<GroupRequestModel> = [];
  groupRequest: GroupRequestModel;
  id: number = 0;
  loggedUserId: number = 0;
  isMember: boolean = false;
  isWaiting: boolean = false;

  constructor(
    private postService: PostService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private groupService: GroupService,
    private groupRequestService: GroupRequestService,
    private authService: AuthService
  ) {
    this.id = this.activatedRoute.snapshot.params['id'];
    this.loggedUserId = this.authService.getUserId();

    this.postService.getAllPostsByGroup(this.id).subscribe((post) => {
      this.posts = post;
    });

    this.groupRequest = {
      id: 0,
      approved: false,
      isBanned: false,
      groupId: 0,
      userId: 0,
    };

    this.groupRequestService.getAllRequests(this.id).subscribe((requests) => {
      this.groupRequests = requests;
      console.log(this.loggedUserId);
      for (const request of requests) {
        if (
          (request.userId === this.loggedUserId || this.loggedUserId == 1) &&
          !request.isBanned
        ) {
          if (request.approved == true || this.loggedUserId == 1) {
            this.isMember = true;
          } else {
            this.isWaiting = true;
          }
        }
      }
    });
  }

  ngOnInit(): void {}

  joinGroup() {
    this.groupRequest.userId = this.loggedUserId;
    this.groupRequest.groupId = this.id;
    this.groupRequestService
      .createGroupRequest(this.groupRequest)
      .subscribe(() => {
        this.router.navigate(['/']);
      });
  }
}
