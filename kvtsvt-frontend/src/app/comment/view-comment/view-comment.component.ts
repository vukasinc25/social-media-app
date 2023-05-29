import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LocalStorageService } from 'ngx-webstorage';
import { throwError } from 'rxjs';
import { AuthService } from 'src/app/auth/shared/auth.service';
import { CommentPayload } from 'src/app/post/comment/comment-payload';
import { CommentService } from 'src/app/post/comment/comment.service';

@Component({
  selector: 'app-view-comment',
  templateUrl: './view-comment.component.html',
  styleUrls: ['./view-comment.component.css'],
})
export class ViewCommentComponent implements OnInit {
  @Input() comments: CommentPayload[] = [];
  loggedUserId: number;
  showReporter: boolean = false;
  commentChildren: CommentPayload[] = [];

  commentForm: FormGroup;
  commentPayload: CommentPayload;

  constructor(
    private router: Router,
    private localStorage: LocalStorageService,
    private commentService: CommentService,
    private authService: AuthService
  ) {
    this.commentForm = new FormGroup({
      text: new FormControl('', Validators.required),
    });
    this.commentPayload = {
      id: 0,
      text: '',
      isDeleted: false,
      userId: 0,
      parentCommentId: 0,
    };
    this.loggedUserId = this.localStorage.retrieve('userId');
  }

  ngOnInit(): void {}

  postComment(parentId: number) {
    this.commentPayload.userId = this.authService.getUserId();
    this.commentPayload.parentCommentId = parentId;
    console.log(parentId);
    this.commentPayload.text = this.commentForm.get('text')?.value;
    this.commentService.postComment(this.commentPayload).subscribe(
      (data) => {
        this.commentForm.get('text')?.setValue('');
      },
      (error) => {
        throwError(error);
      }
    );
  }

  editComment(id: number) {
    this.router.navigateByUrl('edit-comment/' + id);
  }

  getCommentChildren(parentId: number) {
    return this.commentService
      .getCommentChildren(parentId)
      .subscribe((data) => {
        this.commentChildren = data;
      });
  }

  showReport() {
    if (!this.showReporter) {
      this.showReporter = true;
    } else {
      this.showReporter = false;
    }
  }

  deleteComment(id: number) {
    this.commentService.deleteComment(id).subscribe(
      (data) => {
        this.router.navigateByUrl('/');
      },
      (error) => {
        console.log(error);
        throwError(error);
      }
    );
  }
}
