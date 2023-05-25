import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { throwError } from 'rxjs';
import { GroupService } from 'src/app/group/group.service';
import { CommentPayload } from 'src/app/post/comment/comment-payload';
import { CommentService } from 'src/app/post/comment/comment.service';
import { AuthService } from '../shared/auth.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css'],
})
export class EditUserComponent {
  editCommentForm!: FormGroup;
  commentPayload: CommentPayload;
  commentId: number = 0;
  text: string = 'a';

  constructor(
    private router: Router,
    private commentService: CommentService,
    private groupService: GroupService,
    private activateRoute: ActivatedRoute,
    private authService: AuthService
  ) {
    this.commentId = this.activateRoute.snapshot.params['id'];
    this.commentPayload = {
      id: 0,
      text: '',
      isDeleted: false,
      postId: 0,
      userId: 0,
    };
  }

  ngOnInit() {
    this.editCommentForm = new FormGroup({
      text: new FormControl('', Validators.required),
    });

    this.commentService.getComment(this.commentId).subscribe((data) => {
      this.text = data.text;
      this.commentPayload.postId = data.postId;
      this.commentPayload.userId = data.userId;

      this.editCommentForm.get('text')?.setValue(this.text);
    });
  }

  editUser() {
    this.commentPayload.text = this.editCommentForm.get('text')?.value;
    // this.commentPayload.userId = this.authService.getUserId();
    this.commentPayload.id = this.commentId;

    this.commentService.updateComment(this.commentPayload).subscribe(
      (data) => {
        this.router.navigateByUrl('/');
      },
      (error) => {
        throwError(error);
      }
    );
  }

  discardUser() {
    this.router.navigateByUrl('/');
  }
}
