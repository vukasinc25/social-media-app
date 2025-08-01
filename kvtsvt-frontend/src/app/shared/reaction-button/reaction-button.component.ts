import { ReactionType } from './reaction-type';
import { AuthService } from './../../auth/shared/auth.service';
import { PostService } from './../../post/post.service';
import { ReactionModel } from './reaction-model';
import { PostModel } from './../../post/post-model';
import { Component, OnInit, Input } from '@angular/core';
import { throwError } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import {
  faThumbsUp,
  faThumbsDown,
  faHeart,
} from '@fortawesome/free-solid-svg-icons';
import { ReactionService } from '../reaction.service';
import { CommentPayload } from 'src/app/post/comment/comment-payload';
import { ReactionDeleteModel } from './reaction-delete-model';

@Component({
  selector: 'app-reaction-button',
  templateUrl: './reaction-button.component.html',
  styleUrls: ['./reaction-button.component.css'],
})
export class ReactionButtonComponent implements OnInit {
  @Input() post!: PostModel;
  @Input() comment!: CommentPayload;
  reactionModel: ReactionModel;
  reactionDeleteModel: ReactionDeleteModel;
  faThumbsUp = faThumbsUp;
  faThumbsDown = faThumbsDown;
  faHeart = faHeart;

  likeColor: string = 'a';
  dislikeColor: string = 'a';
  heartColor: string = 'a';
  isLoggedIn: boolean = false;

  likeCount: number = 0;
  dislikeCount: number = 0;
  heartCount: number = 0;

  isLiked: boolean = false;
  isDisliked: boolean = false;
  isHearted: boolean = false;

  constructor(
    private reactionService: ReactionService,
    private authService: AuthService,
    private postService: PostService
  ) {
    this.reactionModel = {
      id: 0,
      reactionType: '',
      userId: 0,
      isDeleted: false,
    };
    this.reactionDeleteModel = {
      userId: 0,
    };
    this.authService.loggedIn.subscribe(
      (data: boolean) => (this.isLoggedIn = data)
    );
  }

  ngOnInit(): void {
    this.updateVoteDetails();
  }

  likePost() {
    this.reactionModel.reactionType = 'LIKE';
    this.vote();
    this.likeColor = '';
  }

  unlikeAnything() {
    this.reactionDeleteModel.userId = this.authService.getUserId();
    if (this.post !== undefined) {
      this.reactionDeleteModel.postId = this.post.id;
      this.reactionDeleteModel.groupId = this.post.groupId;
    } else if (this.comment !== undefined) {
      this.reactionDeleteModel.commentId = this.comment.id;
    }
    this.reactionService
      .deleteReaction(this.reactionDeleteModel)
      .subscribe((data) => {
        this.updateVoteDetails();
        this.isLiked = false;
        this.isDisliked = false;
        this.isHearted = false;
      });
  }

  dislikePost() {
    this.reactionModel.reactionType = 'DISLIKE';
    this.vote();
    this.dislikeColor = '';
  }

  heartPost() {
    this.reactionModel.reactionType = 'HEART';
    this.vote();
    this.heartColor = '';
  }

  private vote() {
    console.log(this.comment);
    this.reactionModel.userId = this.authService.getUserId();
    if (this.post !== undefined) {
      this.reactionModel.postId = this.post.id;
      this.reactionModel.groupId = this.post.groupId;
    } else if (this.comment !== undefined) {
      this.reactionModel.commentId = this.comment.id;
    }

    this.reactionService.vote(this.reactionModel).subscribe(
      () => {
        this.updateVoteDetails();
      },
      (error: any) => {
        throwError(error);
      }
    );
  }

  private updateVoteDetails() {
    if (this.post !== undefined) {
      this.reactionService.getAllReactionsByPost(this.post.id).subscribe(
        (data) => {
          this.likeCount = 0;
          this.dislikeCount = 0;
          this.heartCount = 0;
          for (const element of data) {
            if (element.reactionType === 'LIKE') {
              this.likeCount++;
              if (element.userId === this.authService.getUserId()) {
                this.isLiked = true;
              }
            } else if (element.reactionType === 'DISLIKE') {
              this.dislikeCount++;
              if (element.userId === this.authService.getUserId()) {
                this.isDisliked = true;
              }
            } else if (element.reactionType === 'HEART') {
              this.heartCount++;
              if (element.userId === this.authService.getUserId()) {
                this.isHearted = true;
              }
            }
          }
        },
        (error) => {
          throwError(error);
        }
      );
    } else if (this.comment !== undefined) {
      this.reactionService
        .getAllReactionsByComment(this.comment.id)
        .subscribe((data) => {
          this.likeCount = 0;
          this.dislikeCount = 0;
          this.heartCount = 0;
          for (const element of data) {
            if (element.reactionType === 'LIKE') {
              this.likeCount++;
              if (element.userId === this.authService.getUserId()) {
                this.isLiked = true;
              }
            } else if (element.reactionType === 'DISLIKE') {
              this.dislikeCount++;
              if (element.userId === this.authService.getUserId()) {
                this.isDisliked = true;
              }
            } else if (element.reactionType === 'HEART') {
              this.heartCount++;
              if (element.userId === this.authService.getUserId()) {
                this.isHearted = true;
              }
            }
          }
        });
    }
  }
}
