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

@Component({
  selector: 'app-reaction-button',
  templateUrl: './reaction-button.component.html',
  styleUrls: ['./reaction-button.component.css'],
})
export class ReactionButtonComponent implements OnInit {
  @Input() post!: PostModel;
  @Input() comment!: CommentPayload;
  reactionModel: ReactionModel;
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

  constructor(
    private reactionService: ReactionService,
    private authService: AuthService,
    private postService: PostService
  ) {
    this.reactionModel = {
      id: 0,
      reactionType: '',
      postId: 0,
      userId: 0,
      commentId: 0,
      isDeleted: false,
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
    this.reactionModel.userId = this.authService.getUserId();
    this.reactionModel.postId = this.post.id;
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
          for (const element of data) {
            if (element.reactionType === 'LIKE') {
              this.likeCount++;
            } else if (element.reactionType === 'DISLIKE') {
              this.dislikeCount++;
            } else if (element.reactionType === 'HEART') {
              this.heartCount++;
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
          for (const element of data) {
            if (element.reactionType === 'LIKE') {
              this.likeCount++;
            } else if (element.reactionType === 'DISLIKE') {
              this.dislikeCount++;
            } else if (element.reactionType === 'HEART') {
              this.heartCount++;
            }
          }
        });
    }
  }
}
