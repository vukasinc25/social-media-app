import { CreatePostDto } from './../create-post-dto';
import { GroupModel } from './../../group/group-model';
import { GroupService } from './../../group/group.service';
import { PostService } from './../post.service';
import { throwError } from 'rxjs';
import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/auth/shared/auth.service';
import { ImageModel } from 'src/app/auth/shared/image-model';
import { ImageService } from 'src/app/auth/shared/image.service';

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
  imageModel: ImageModel;
  imagesString: string = '';
  imagesStringSeperated: string[] = [];
  counter: number = 0;

  constructor(
    private router: Router,
    private postService: PostService,
    private groupService: GroupService,
    private authService: AuthService,
    private activateRoute: ActivatedRoute,
    private imageService: ImageService
  ) {
    this.imageModel = {
      id: 0,
      path: '',
      userId: 0,
    };
    this.postPayload = {
      title: '',
      content: '',
      userId: 0,
      groupId: 0,
    };
  }

  ngOnInit() {
    this.groupId = this.activateRoute.snapshot.params['id'];
    this.createPostForm = new FormGroup({
      groupId: new FormControl(''),
      title: new FormControl('', Validators.required),
      content: new FormControl('', Validators.required),
      images: new FormControl('', Validators.required),
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
    this.postPayload.title = this.createPostForm.get('title')?.value;
    this.postPayload.content = this.createPostForm.get('content')?.value;
    this.postPayload.userId = this.authService.getUserId();
    this.postPayload.groupId = this.createPostForm.get('groupId')?.value;

    this.postService.createPost(this.postPayload).subscribe(
      (data) => {
        this.router.navigateByUrl('/');
        this.postService.getAllPosts().subscribe((data) => {
          for (const post of data) {
            this.counter += 1;
          }
          this.imageModel.postId = this.counter;
          this.imageModel.userId = this.authService.getUserId();
          this.imagesString = this.createPostForm.get('images')?.value;
          this.imagesStringSeperated = this.imagesString.split(',');
          for (const image of this.imagesStringSeperated) {
            this.imageModel.path = image;
            this.imageService.createImage(this.imageModel).subscribe();
          }
        });
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
