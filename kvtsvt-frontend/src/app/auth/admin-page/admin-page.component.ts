import { Component, OnInit } from '@angular/core';
import { GroupAdminModel } from 'src/app/group/group-admin-model';
import { GroupModel } from 'src/app/group/group-model';
import { GroupService } from 'src/app/group/group.service';
import { FormsModule } from '@angular/forms';
import { ReportModel } from 'src/app/shared/create-report/report-model';
import { ReportService } from 'src/app/shared/report.service';
import { Router } from '@angular/router';
import { CommentService } from 'src/app/post/comment/comment.service';
import { AuthService } from '../shared/auth.service';
import { RegisterRequestModel } from '../register/register-request-model';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css'],
})
export class AdminPageComponent implements OnInit {
  groupAdmins: Array<GroupAdminModel> = [];
  groups: Array<GroupModel> = [];
  reports: Array<ReportModel> = [];
  group: GroupModel;
  blockedUsers: Array<RegisterRequestModel> = [];

  constructor(
    private groupService: GroupService,
    private reportService: ReportService,
    private router: Router,
    private commentService: CommentService,
    private authService: AuthService
  ) {
    this.group = {
      id: 0,
      name: '',
      description: '',
      adminId: 0,
    };
  }

  ngOnInit() {
    this.getAllGroupAdmins();
    this.getAllGroups();
    this.getAllReports();
    this.getBlockedUsers();
  }

  getAllReports() {
    return this.reportService.getAll().subscribe((data) => {
      this.reports = data;
    });
  }

  getAllGroupAdmins() {
    return this.groupService.getAllGroupAdmins().subscribe((data) => {
      this.groupAdmins = data;
    });
  }

  removeAdmin(id: number) {
    this.groupService.removeGroupAdmin(id).subscribe(() => {
      this.getAllGroupAdmins();
    });
  }

  getAllGroups() {
    return this.groupService.getAllGroups().subscribe((data) => {
      this.groups = data;
    });
  }

  suspendGroup(id: number, reason: string) {
    this.group.id = id;
    this.group.suspensionReason = reason;

    this.groupService.deleteGroup(this.group).subscribe(() => {
      this.groupService.getAllGroupAdmins().subscribe((groupAdmins) => {
        for (const groupAdmin of groupAdmins) {
          if (groupAdmin.groupId === this.group.id) {
            this.removeAdmin(groupAdmin.id);
          }
        }
      });
      this.ngOnInit();
    });
  }

  openUserProfile(userId: number, reportId: number) {
    this.router.navigate(['user-profile/' + userId]);
    this.reportService.deleteReport(reportId).subscribe(() => {
      this.getAllReports();
    });
  }

  openPost(postId: number, reportId: number) {
    this.router.navigate(['view-post/' + postId]);
    this.reportService.deleteReport(reportId).subscribe(() => {
      this.getAllReports();
    });
  }

  openComment(commentId: number, reportId: number) {
    this.commentService.deleteComment(commentId).subscribe(() => {
      this.getAllReports();
    });
  }

  getBlockedUsers() {
    this.authService.getBlockedUsers().subscribe((data) => {
      this.blockedUsers = data;
    });
  }

  unblockUser(userId: number) {
    this.authService.blockUser(userId).subscribe(() => {
      this.getBlockedUsers();
    });
  }
}
