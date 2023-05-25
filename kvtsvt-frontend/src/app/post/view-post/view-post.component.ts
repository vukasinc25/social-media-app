import { PostModel } from './../post-model';
import { CommentPayload } from './../comment/comment-payload';
import { CommentService } from './../comment/comment.service';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from './../post.service';
import { Component, OnInit } from '@angular/core';
import { throwError } from 'rxjs';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { LocalStorageService } from 'ngx-webstorage';
import { AuthService } from 'src/app/auth/shared/auth.service';
import { RegisterRequestModel } from 'src/app/auth/register/register-request-model';

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.css'],
})
export class ViewPostComponent implements OnInit {
  postId: number;
  userId: number = 0;
  user!: RegisterRequestModel;
  loggedUserId: number;
  post!: PostModel;
  commentForm: FormGroup;
  commentPayload: CommentPayload;
  comments: CommentPayload[] = [];

  constructor(
    private postService: PostService,
    private activateRoute: ActivatedRoute,
    private commentService: CommentService,
    private localStorage: LocalStorageService,
    private authService: AuthService,
    private router: Router
  ) {
    this.postId = this.activateRoute.snapshot.params['id'];
    this.loggedUserId = this.localStorage.retrieve('userId');

    console.log(this.postId);

    this.commentForm = new FormGroup({
      text: new FormControl('', Validators.required),
    });
    this.commentPayload = {
      text: '',
      isDeleted: false,
      postId: this.postId,
      userId: 0,
    };
  }

  ngOnInit(): void {
    this.getPostById();
    this.userId = this.post.userId;
    this.getCommentsForPost();
    this.getUser();
  }

  postComment() {
    this.commentPayload.userId = this.post.userId;
    this.commentPayload.text = this.commentForm.get('text')?.value;
    this.commentService.postComment(this.commentPayload).subscribe(
      (data) => {
        this.commentForm.get('text')?.setValue('');
        this.getCommentsForPost();
      },
      (error) => {
        throwError(error);
      }
    );
  }

  selectPost() {
    this.router.navigateByUrl('edit-post/' + this.postId);
  }

  deletePost() {
    this.postService.deletePost(this.postId).subscribe(
      (data) => {
        this.router.navigateByUrl('/');
      },
      (error) => {
        throwError(error);
      }
    );
  }

  private getPostById() {
    this.postService.getPost(this.postId).subscribe(
      (data) => {
        this.post = data;
      },
      (error) => {
        throwError(error);
      }
    );
  }

  private getCommentsForPost() {
    this.commentService.getAllCommentsForPost(this.postId).subscribe(
      (data) => {
        this.comments = data;
      },
      (error) => {
        throwError(error);
      }
    );
  }

  getUser() {
    this.authService.getUser(this.userId).subscribe((data) => {
      this.user = data;
    });
  }
}
