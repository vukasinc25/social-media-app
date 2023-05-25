import { ActivatedRoute, Router } from '@angular/router';
import { PostModel } from 'src/app/post/post-model';
import { GroupModel } from './../group-model';
import { GroupService } from './../group.service';
import { Component, OnInit } from '@angular/core';
import { throwError } from 'rxjs';
import { PostService } from 'src/app/post/post.service';

@Component({
  selector: 'app-view-group',
  templateUrl: './view-group.component.html',
  styleUrls: ['./view-group.component.css'],
})
export class ViewGroupComponent implements OnInit {
  posts: Array<PostModel> = [];
  id: number = 0;

  constructor(
    private postService: PostService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {
    this.id = this.activatedRoute.snapshot.params['id'];

    this.postService.getAllPostsByGroup(this.id).subscribe((post) => {
      this.posts = post;
    });
  }

  ngOnInit(): void {}
}
