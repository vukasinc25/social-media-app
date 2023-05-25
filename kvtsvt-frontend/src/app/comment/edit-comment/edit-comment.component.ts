import { GroupModel } from './../../group/group-model';
import { GroupService } from './../../group/group.service';
import { throwError } from 'rxjs';
import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/auth/shared/auth.service';
import { CreatePostDto } from 'src/app/post/create-post-dto';
import { CommentPayload } from 'src/app/post/comment/comment-payload';
import { CommentService } from 'src/app/post/comment/comment.service';

@Component({
  selector: 'app-edit-comment',
  templateUrl: './edit-comment.component.html',
  styleUrls: ['./edit-comment.component.css'],
})
export class EditCommentComponent {
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

  editComment() {
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

  discardComment() {
    this.router.navigateByUrl('/');
  }
}
