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

@Component({
  selector: 'app-reaction-button',
  templateUrl: './reaction-button.component.html',
  styleUrls: ['./reaction-button.component.css'],
})
export class ReactionButtonComponent implements OnInit {
  @Input() post!: PostModel;
  reactionModel: ReactionModel;
  faThumbsUp = faThumbsUp;
  faThumbsDown = faThumbsDown;
  faHeart = faHeart;

  likeColor: string = 'a';
  dislikeColor: string = 'a';
  heartColor: string = 'a';
  isLoggedIn: boolean = false;

  constructor(
    private reactionService: ReactionService,
    private authService: AuthService,
    private postService: PostService
  ) {
    this.reactionModel = {
      reactionType: ReactionType.LIKE,
      postId: 0,
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
    this.reactionModel.reactionType = ReactionType.LIKE;
    this.vote();
    this.likeColor = '';
  }

  dislikePost() {
    this.reactionModel.reactionType = ReactionType.DISLIKE;
    this.vote();
    this.dislikeColor = '';
  }

  heartPost() {
    this.reactionModel.reactionType = ReactionType.HEART;
    this.vote();
    this.heartColor = '';
  }

  private vote() {
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
    this.postService.getPost(this.post.id).subscribe((post) => {
      this.post = post;
    });
  }
}
