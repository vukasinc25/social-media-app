import { Component, OnInit } from '@angular/core';
import { PostModel } from '../post/post-model';
import { PostService } from '../post/post.service';
import { AuthService } from '../auth/shared/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  posts: Array<PostModel> = [];
  isLoggedIn: boolean = false;

  constructor(
    private postService: PostService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.isLoggedIn = this.authService.isLoggedIn();
    this.getAllPosts();
  }

  getAllPosts() {
    this.postService.getAllPosts().subscribe((posts) => {
      this.posts = posts;
    });
  }

  getAllPostsDesc() {
    this.postService.getAllPostsDesc().subscribe((posts) => {
      this.posts = posts;
    });
  }
  // sortPostsByCreationDateDesc(): void {
  //   this.posts.sort(
  //     (a, b) => b.creationDate.getTime() - a.creationDate.getTime()
  //   );
  // }

  // sortPostsByCreationDateAsc(): void {
  //   this.posts.sort(
  //     (a, b) => a.creationDate.getTime() - b.creationDate.getTime()
  //   );
  // }
}
