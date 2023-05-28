import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from 'ngx-webstorage';
import { throwError } from 'rxjs';
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
  constructor(
    private router: Router,
    private localStorage: LocalStorageService,
    private commentService: CommentService
  ) {
    this.loggedUserId = this.localStorage.retrieve('userId');
  }

  ngOnInit(): void {}

  editComment(id: number) {
    this.router.navigateByUrl('edit-comment/' + id);
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
