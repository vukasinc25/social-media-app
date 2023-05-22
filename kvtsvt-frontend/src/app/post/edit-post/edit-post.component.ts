import { CreatePostDto } from './../create-post-dto';
import { GroupModel } from './../../group/group-model';
import { GroupService } from './../../group/group.service';
import { PostService } from './../post.service';
import { throwError } from 'rxjs';
import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/shared/auth.service';
import { PostModel } from '../post-model';

@Component({
  selector: 'app-edit-post',
  templateUrl: './edit-post.component.html',
  styleUrls: ['./edit-post.component.css'],
})
export class EditPostComponent implements OnInit {
  @Input() post!: PostModel;
  editPostForm!: FormGroup;
  postPayload: CreatePostDto;
  groups: Array<GroupModel> = [];

  constructor(
    private router: Router,
    private postService: PostService,
    private groupService: GroupService,
    private authService: AuthService
  ) {
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
  }

  editPost() {
    this.postPayload.content = this.editPostForm.get('content')?.value;
    this.postPayload.userId = this.authService.getUserId();
    this.postPayload.groupId = this.editPostForm.get('groupId')?.value;

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
