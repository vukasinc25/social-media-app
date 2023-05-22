import { PostModel } from './../post-model';
import { CommentPayload } from './../comment/comment-payload';
import { CommentService } from './../comment/comment.service';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from './../post.service';
import { Component, OnInit } from '@angular/core';
import { throwError } from 'rxjs';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.css'],
})
export class ViewPostComponent implements OnInit {
  postId: number;
  userId: number;
  post!: PostModel;
  commentForm: FormGroup;
  commentPayload: CommentPayload;
  comments: CommentPayload[] = [];

  constructor(
    private postService: PostService,
    private activateRoute: ActivatedRoute,
    private commentService: CommentService,
    private router: Router
  ) {
    this.postId = this.activateRoute.snapshot.params['id'];
    this.userId = this.activateRoute.snapshot.params['userId'];

    this.commentForm = new FormGroup({
      text: new FormControl('', Validators.required),
    });
    this.commentPayload = {
      text: '',
      isDeleted: false,
      postId: this.postId,
      userId: this.userId,
    };
  }

  ngOnInit(): void {
    this.getPostById();
    this.getCommentsForPost();
  }

  postComment() {
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
    this.postService.deletePost(this.postId);
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
}
