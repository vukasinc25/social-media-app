import { CreatePostDto } from './../create-post-dto';
import { GroupModel } from './../../group/group-model';
import { GroupService } from './../../group/group.service';
import { PostService } from './../post.service';
import { throwError } from 'rxjs';
import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/auth/shared/auth.service';
import { PostModel } from '../post-model';

@Component({
  selector: 'app-edit-post',
  templateUrl: './edit-post.component.html',
  styleUrls: ['./edit-post.component.css'],
})
export class EditPostComponent implements OnInit {
  editPostForm!: FormGroup;
  postPayload: CreatePostDto;
  groups: Array<GroupModel> = [];
  postId: number = 0;
  private content: string = 'a';

  constructor(
    private router: Router,
    private postService: PostService,
    private groupService: GroupService,
    private activateRoute: ActivatedRoute,
    private authService: AuthService
  ) {
    this.postId = this.activateRoute.snapshot.params['id'];
    this.postPayload = {
      content: '',
      userId: 0,
      groupId: 0,
    };
  }

  ngOnInit() {
    this.editPostForm = new FormGroup({
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

    this.postService.getPost(this.postId).subscribe((data) => {
      this.content = data.content;
      this.postPayload.groupId = data.groupId;

      this.editPostForm.get('content')?.setValue(this.content);
    });
  }

  editPost() {
    this.postPayload.content = this.editPostForm.get('content')?.value;
    this.postPayload.userId = this.authService.getUserId();
    this.postPayload.groupId = this.editPostForm.get('groupId')?.value;
    this.postPayload.id = this.postId;

    this.postService.updatePost(this.postPayload).subscribe(
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
