import { LocalStorageService } from 'ngx-webstorage';
import { AuthService } from 'src/app/auth/shared/auth.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterState } from '@angular/router';
import { CommentPayload } from 'src/app/post/comment/comment-payload';
import { CommentService } from 'src/app/post/comment/comment.service';
import { PostModel } from 'src/app/post/post-model';
import { PostService } from 'src/app/post/post.service';
import { RegisterRequestModel } from '../register/register-request-model';
import { FriendService } from '../shared/friend.service';
import { FriendRequestModel } from '../shared/friend-request-model';
import { ImageService } from '../shared/image.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css'],
})
export class UserProfileComponent implements OnInit {
  // name: string;
  posts: PostModel[] = [];
  comments: CommentPayload[] = [];
  user: RegisterRequestModel;
  postLength: number = 0;
  commentLength: number = 0;
  loggedId: number = 0;
  id: number = 0;
  showReporter: boolean = false;

  friendRequest: FriendRequestModel;
  friendRequests: Array<FriendRequestModel> = [];

  description: string = '';
  newUsername: string = '';
  image: string = '';

  constructor(
    private activatedRoute: ActivatedRoute,
    private postService: PostService,
    private commentService: CommentService,
    private authService: AuthService,
    private router: Router,
    private friendService: FriendService,
    private localStorage: LocalStorageService,
    private imageService: ImageService
  ) {
    this.loggedId = this.authService.getUserId();
    // this.name = this.authService.getUserName();
    this.id = this.activatedRoute.snapshot.params['id'];

    this.user = {
      id: this.activatedRoute.snapshot.params['id'],
      email: '',
      username: '',
      firstname: '',
      lastname: '',
      password: '',
    };
    this.friendRequest = {
      id: 0,
      approved: false,
      requestForId: 0,
      requestFromId: 0,
    };

    this.postService.getAllPostsByUser(this.id).subscribe((data) => {
      this.posts = data;
      this.postLength = data.length;
    });
    this.commentService.getAllCommentsByUser(this.id).subscribe((data) => {
      this.comments = data;
      this.commentLength = data.length;
    });
  }

  ngOnInit(): void {
    this.description = this.localStorage.retrieve('description');
    this.newUsername = this.localStorage.retrieve('newUsername');
    this.getFriendRequests();
    this.getImage();
  }

  showReport() {
    if (!this.showReporter) {
      this.showReporter = true;
    } else {
      this.showReporter = false;
    }
  }

  blockUser(id: number) {
    this.authService.blockUser(id).subscribe(() => {
      this.router.navigateByUrl('admin');
    });
  }

  editUser(id: number) {
    this.router.navigateByUrl('edit-user/' + id);
  }

  getImage() {
    this.imageService.getImageByUser(this.id).subscribe((data) => {
      this.image = data.path;
      console.log(this.image);
    });
  }

  getFriendRequests() {
    this.friendService.getAllFriendRequests().subscribe((data) => {
      for (const friendRequest of data) {
        if (friendRequest.requestForId == this.id && !friendRequest.approved) {
          this.friendRequests.push(friendRequest);
        }
      }
    });
  }

  addFriend() {
    this.friendRequest.requestForId = this.id;
    this.friendRequest.requestFromId = this.loggedId;
    this.friendService.createFriendRequest(this.friendRequest).subscribe();
  }

  acceptRequest(id: number) {
    this.friendService.editFriendRequest(id).subscribe(() => {
      this.getFriendRequests();
    });
  }

  declineRequest(id: number) {
    this.friendService.deleteFriendRequest(id).subscribe(() => {
      this.getFriendRequests();
    });
  }

  openProfile(id: number) {
    this.router.navigateByUrl('user-profile/' + id);
  }
}
