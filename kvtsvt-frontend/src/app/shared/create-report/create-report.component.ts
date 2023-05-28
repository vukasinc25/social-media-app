import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ReportModel } from './report-model';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/shared/auth.service';
import { GroupService } from 'src/app/group/group.service';
import { PostService } from 'src/app/post/post.service';
import { NgClass } from '@angular/common';
import { RegisterRequestModel } from 'src/app/auth/register/register-request-model';
import { PostModel } from 'src/app/post/post-model';
import { CommentPayload } from 'src/app/post/comment/comment-payload';
import { ReactionService } from '../reaction.service';
import { ReportService } from '../report.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-create-report',
  templateUrl: './create-report.component.html',
  styleUrls: ['./create-report.component.css'],
})
export class CreateReportComponent implements OnInit {
  @Input() user!: RegisterRequestModel;
  @Input() post!: PostModel;
  @Input() comment!: CommentPayload;

  createReportForm!: FormGroup;
  reportModel: ReportModel;

  constructor(
    private router: Router,
    private postService: PostService,
    private groupService: GroupService,
    private reportService: ReportService,
    private authService: AuthService,
    private toastr: ToastrService
  ) {
    this.reportModel = {
      id: 0,
      reportReason: '',
      isAccepted: false,
      byUserId: 0,
    };
  }
  ngOnInit() {
    this.createReportForm = new FormGroup({
      reportReason: new FormControl('', Validators.required),
    });
  }

  discardReport() {
    this.router.navigate(['/']);
    this.toastr.success('Canceled Report');
  }
  submitReport() {
    this.reportModel.byUserId = this.authService.getUserId();
    this.reportModel.reportReason =
      this.createReportForm.get('reportReason')?.value;
    if (this.post !== undefined) {
      this.reportModel.postId = this.post.id;
      this.reportModel.groupId = this.post.groupId;
    } else if (this.comment !== undefined) {
      this.reportModel.commentId = this.comment.id;
      // this.postService.getPost(this.comment.postId);
    } else if (this.user !== undefined) {
      this.reportModel.reportedUserId = this.user.id;
    }

    this.reportService.createReport(this.reportModel).subscribe(() => {
      this.router.navigate(['/']);
      this.toastr.success('Report created successfully');
    });
  }
}
