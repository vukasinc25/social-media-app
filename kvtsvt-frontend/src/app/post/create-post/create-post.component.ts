import { CreatePostDto } from './../create-post-dto';
import { GroupModel } from './../../group/group-model';
import { GroupService } from './../../group/group.service';
import { PostService } from './../post.service';
import { throwError } from 'rxjs';
import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/auth/shared/auth.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css'],
})
export class CreatePostComponent implements OnInit {
  createPostForm!: FormGroup;
  postPayload: CreatePostDto;
  groups: Array<GroupModel> = [];
  groupId: number = 0;

  constructor(
    private router: Router,
    private postService: PostService,
    private groupService: GroupService,
    private authService: AuthService,
    private activateRoute: ActivatedRoute
  ) {
    this.postPayload = {
      content: '',
      userId: 0,
      groupId: 0,
    };
  }

  ngOnInit() {
    this.groupId = this.activateRoute.snapshot.params['id'];
    this.createPostForm = new FormGroup({
      groupId: new FormControl(''),
      content: new FormControl('', Validators.required),
    });
    this.groupService.getAllGroups().subscribe(
      (data) => {
        this.groups = data;
      },
      (error) => {
        throwError(error);
      }
    );
  }

  createPost() {
    this.postPayload.content = this.createPostForm.get('content')?.value;
    this.postPayload.userId = this.authService.getUserId();
    this.postPayload.groupId = this.createPostForm.get('groupId')?.value;

    this.postService.createPost(this.postPayload).subscribe(
      (data) => {
        this.router.navigateByUrl('/');
      },
      (error) => {
        throwError(error);
      }
    );
  }

  discardPost() {
    this.router.navigateByUrl('/');
  }
}
