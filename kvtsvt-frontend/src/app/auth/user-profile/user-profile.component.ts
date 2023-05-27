import { AuthService } from 'src/app/auth/shared/auth.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterState } from '@angular/router';
import { CommentPayload } from 'src/app/post/comment/comment-payload';
import { CommentService } from 'src/app/post/comment/comment.service';
import { PostModel } from 'src/app/post/post-model';
import { PostService } from 'src/app/post/post.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css'],
})
export class UserProfileComponent implements OnInit {
  name: string;
  posts: PostModel[] = [];
  comments: CommentPayload[] = [];
  postLength: number = 0;
  commentLength: number = 0;
  loggedId: number = 0;
  id: number = 0;

  constructor(
    private activatedRoute: ActivatedRoute,
    private postService: PostService,
    private commentService: CommentService,
    private authService: AuthService,
    private router: Router
  ) {
    this.loggedId = this.authService.getUserId();
    this.name = this.authService.getUserName();
    this.id = this.activatedRoute.snapshot.params['id'];

    this.postService.getAllPostsByUser(this.id).subscribe((data) => {
      this.posts = data;
      this.postLength = data.length;
    });
    this.commentService.getAllCommentsByUser(this.id).subscribe((data) => {
      this.comments = data;
      this.commentLength = data.length;
    });
  }

  ngOnInit(): void {}

  editUser(id: number) {
    this.router.navigateByUrl('edit-user/' + id);
  }
}
