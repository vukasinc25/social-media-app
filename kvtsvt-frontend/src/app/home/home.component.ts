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
  ) {
    this.postService.getAllPosts().subscribe((posts) => {
      this.posts = posts.sort((a, b) => {
        // Sort by creation date in ascending order
        return a.creationDate.getTime() - b.creationDate.getTime();
        // Uncomment the line below to sort in descending order
        // return b.creationDate.getTime() - a.creationDate.getTime();
      });
    });
  }

  ngOnInit(): void {
    this.isLoggedIn = this.authService.isLoggedIn();
  }
}
