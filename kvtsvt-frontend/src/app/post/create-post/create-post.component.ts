import { CreatePostDto } from './../create-post-dto';
import { GroupModel } from './../../group/group-model';
import { GroupService } from './../../group/group.service';
import { PostService } from './../post.service';
import { throwError } from 'rxjs';
import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css'],
})
export class CreatePostComponent implements OnInit {
  createPostForm!: FormGroup;
  postPayload: CreatePostDto;
  groups: Array<GroupModel> = [];

  constructor(
    private router: Router,
    private postService: PostService,
    private groupService: GroupService
  ) {
    this.postPayload = {
      groupName: '',
      content: '',
    };
  }

  ngOnInit() {
    this.createPostForm = new FormGroup({
      postName: new FormControl('', Validators.required),
      groupName: new FormControl('', Validators.required),
      url: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
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
    this.postPayload.groupName = this.createPostForm.get('groupName')?.value;
    this.postPayload.content = this.createPostForm.get('content')?.value;

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
