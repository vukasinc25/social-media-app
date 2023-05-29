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
import { GroupService } from 'src/app/group/group.service';
import { GroupModel } from 'src/app/group/group-model';
import { ImageService } from 'src/app/auth/shared/image.service';
import { ImageModel } from 'src/app/auth/shared/image-model';

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
  group!: GroupModel;
  post!: PostModel;
  commentForm: FormGroup;
  commentPayload: CommentPayload;
  comments: CommentPayload[] = [];
  showReporter: boolean = false;

  images: ImageModel[] = [];

  constructor(
    private postService: PostService,
    private activateRoute: ActivatedRoute,
    private commentService: CommentService,
    private localStorage: LocalStorageService,
    private groupService: GroupService,
    private authService: AuthService,
    private router: Router,
    private imageService: ImageService
  ) {
    this.postId = this.activateRoute.snapshot.params['id'];
    this.loggedUserId = this.localStorage.retrieve('userId');

    this.commentForm = new FormGroup({
      text: new FormControl('', Validators.required),
    });
    this.post = {
      id: this.activateRoute.snapshot.params['id'],
      content: '',
      isDeleted: false,
      userId: 0,
      groupId: 0,
      creationDate: new Date(),
    };
    this.user = {
      id: 0,
      firstname: '',
      lastname: '',
      username: '',
      email: '',
      password: '',
    };
    this.group = {
      id: 0,
      adminId: 0,
    };
    this.commentPayload = {
      id: 0,
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
    this.getImagesForPost();
  }

  getImagesForPost() {
    return this.imageService
      .getAllImagesByPost(this.postId)
      .subscribe((data) => {
        this.images = data;
      });
  }

  showReport() {
    if (!this.showReporter) {
      this.showReporter = true;
    } else {
      this.showReporter = false;
    }
  }

  postComment() {
    this.commentPayload.userId = this.authService.getUserId();
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
        this.getUser(data.userId);
        this.getGroup(data.groupId);
      },
      (error) => {
        throwError(error);
      }
    );
  }

  getCommentsForPost() {
    this.commentService.getAllCommentsForPost(this.postId).subscribe(
      (data) => {
        this.comments = data;
      },
      (error) => {
        throwError(error);
      }
    );
  }

  getCommentsForPostDesc() {
    this.commentService
      .getAllCommentsForPostDesc(this.postId)
      .subscribe((data) => {
        this.comments = data;
      });
  }

  getCommentsLikesDesc() {
    this.commentService.getCommentsByReaction(1).subscribe((data) => {
      this.comments = data;
    });
  }
  getCommentsLikesAsc() {
    this.commentService.getCommentsByReaction(2).subscribe((data) => {
      this.comments = data;
    });
  }
  getCommentsDislikesDesc() {
    this.commentService.getCommentsByReaction(3).subscribe((data) => {
      this.comments = data;
    });
  }
  getCommentsDislikesAsc() {
    this.commentService.getCommentsByReaction(4).subscribe((data) => {
      this.comments = data;
    });
  }
  getCommentsHeartDesc() {
    this.commentService.getCommentsByReaction(5).subscribe((data) => {
      this.comments = data;
    });
  }
  getCommentsHeartAsc() {
    this.commentService.getCommentsByReaction(6).subscribe((data) => {
      this.comments = data;
    });
  }

  getUser(id: number) {
    this.authService.getUser(id).subscribe((data) => {
      this.user = data;
    });
  }

  getGroup(id: number) {
    this.groupService.getGroup(id).subscribe((data) => {
      this.group = data;
    });
  }
}
