import { Component, OnInit } from '@angular/core';
import { GroupRequestModel } from '../group-request-model';
import { GroupRequestService } from '../group-request.service';
import { RegisterRequestModel } from 'src/app/auth/register/register-request-model';
import { AuthService } from 'src/app/auth/shared/auth.service';
import { GroupService } from '../group.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ReportService } from 'src/app/shared/report.service';
import { CommentService } from 'src/app/post/comment/comment.service';
import { ReportModel } from 'src/app/shared/create-report/report-model';
import { PostService } from 'src/app/post/post.service';
import { PostModel } from 'src/app/post/post-model';

@Component({
  selector: 'app-admin-group',
  templateUrl: './admin-group.component.html',
  styleUrls: ['./admin-group.component.css'],
})
export class AdminGroupComponent implements OnInit {
  groupRequests: Array<GroupRequestModel> = [];
  groupUsers: Array<GroupRequestModel> = [];
  groupRequest: GroupRequestModel;
  users: Array<RegisterRequestModel> = [];
  reports: Array<ReportModel> = [];
  posts: Array<PostModel> = [];
  id: number = 0;
  constructor(
    private groupRequestService: GroupRequestService,
    private authService: AuthService,
    private groupService: GroupService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private reportService: ReportService,
    private commentService: CommentService,
    private postService: PostService
  ) {
    this.id = this.activatedRoute.snapshot.params['id'];
    console.log(this.id);
    this.groupRequest = {
      id: 0,
      approved: false,
      isBanned: false,
      groupId: 0,
      userId: 0,
    };
  }

  ngOnInit(): void {
    this.getGroupRequests();
    this.getUsersFromGroup();
    this.getAllReports();
  }

  getGroupRequests() {
    this.groupRequestService.getAllRequests(this.id).subscribe((data) => {
      for (const request of data) {
        if (!request.approved) {
          this.groupRequests = data;
        }
      }
    });
  }

  getUsersFromGroup() {
    this.groupRequestService.getAllRequests(this.id).subscribe((data) => {
      for (const request of data) {
        if (request.approved) {
          this.groupUsers = data;
        }
      }
    });
  }

  // getUsersFromGroup() {
  //   this.groupRequestService.getAllRequests(this.id).subscribe((data) => {
  //     for (const request of data) {
  //       this.authService.getUser(request.userId).subscribe((user) => {
  //         this.users.push(user);
  //       });
  //     }
  //   });
  // }

  banUser(id: number) {
    this.groupRequest.id = id;
    this.groupRequestService.banGR(this.groupRequest).subscribe(() => {
      this.getUsersFromGroup();
    });
  }

  unbanUser(id: number) {}

  acceptRequest(id: number) {
    this.groupRequest.id = id;
    this.groupRequestService.approveGR(this.groupRequest).subscribe(() => {
      this.ngOnInit();
    });
  }

  declineRequest(id: number) {
    this.groupRequestService.deleteGroup(id).subscribe(() => {
      this.ngOnInit();
    });
  }

  getAllReports() {
    return this.reportService.getAll().subscribe((data) => {
      this.postService.getAllPostsByGroup(this.id).subscribe((post) => {
        this.posts = post;
        for (const report of data) {
          for (const post of this.posts) {
            if (report.postId === post.id) {
              this.reports.push(report);
            }
          }
        }
      });
    });
  }

  openPost(postId: number, reportId: number) {
    // this.router.navigate(['view-post/' + postId]);
    this.postService.deletePost(postId).subscribe(() => {
      this.getAllReports();
    });
    this.reportService.deleteReport(reportId).subscribe(() => {
      this.getAllReports();
    });
    this.ngOnInit();
  }

  openComment(commentId: number, reportId: number) {
    this.commentService.deleteComment(commentId).subscribe(() => {
      this.getAllReports();
    });
  }
}
