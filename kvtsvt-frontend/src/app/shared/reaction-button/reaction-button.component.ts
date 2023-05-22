import { ReactionType } from './reaction-type';
import { AuthService } from './../../auth/shared/auth.service';
import { PostService } from './../../post/post.service';
import { ReactionModel } from './reaction-model';
import { PostModel } from './../../post/post-model';
import { Component, OnInit } from '@angular/core';
import { throwError } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { faThumbsUp, faThumbsDown } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-reaction-button',
  templateUrl: './reaction-button.component.html',
  styleUrls: ['./reaction-button.component.css'],
})
export class ReactionButtonComponent implements OnInit {
  // @Input() post: PostModel;
  // reactionModel: ReactionModel;
  // faThumbsUp = faThumbsUp;
  // faThumbsDown = faThumbsDown;
  // faHeart: faHeart;

  constructor() // private voteService: VoteService,
  // private authService: AuthService,
  // private postService: PostService,
  // private toastr: ToastrService,
  // private upvoteColor: String,
  // private downvoteColor: String,
  // private heartColor: String
  {
    // this.reactionModel = {
    //   reactionType: undefined,
    //   postId: 0,
    //   userId: 0,
    // };
    // this.authService.loggedIn.subscribe(
    //   (data: boolean) => (this.isLoggedIn = data)
    // );
  }

  ngOnInit(): void {
    // this.updateVoteDetails();
  }

  // likePost() {
  //   this.reactionModel.reactionType = ReactionType.LIKE;
  //   this.vote();
  //   this.downvoteColor = '';
  // }

  // dislikePost() {
  //   this.reactionModel.reactionType = ReactionType.DISLIKE;
  //   this.vote();
  //   this.upvoteColor = '';
  // }

  // heartPost() {
  //   this.reactionModel.reactionType = ReactionType.HEART;
  //   this.vote();
  //   this.upvoteColor = '';
  // }

  // private vote() {
  //   this.reactionModel.postId = this.post.id;
  //   this.voteService.vote(this.reactionModel).subscribe(
  //     () => {
  //       this.updateVoteDetails();
  //     },
  //     (error: any) => {
  //       this.toastr.error(error.error.message);
  //       throwError(error);
  //     }
  //   );
  // }

  // private updateVoteDetails() {
  //   this.postService.getPost(this.post.id).subscribe((post) => {
  //     this.post = post;
  //   });
  // }
}
