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
import { ImageService } from 'src/app/auth/shared/image.service';
import { ImageModel } from 'src/app/auth/shared/image-model';

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

  imageModel: ImageModel;
  imageIds: number[] = [];
  imagePaths: Array<string> = [];
  imagePathsNonSeperated: string = '';
  seperatedImagePaths: string[] = [];
  counter: number = 1;

  constructor(
    private router: Router,
    private postService: PostService,
    private groupService: GroupService,
    private activateRoute: ActivatedRoute,
    private authService: AuthService,
    private imageService: ImageService
  ) {
    this.imageModel = {
      id: 0,
      path: '',
    };
    this.postId = this.activateRoute.snapshot.params['id'];
    this.postPayload = {
      title: '',
      content: '',
      userId: 0,
      groupId: 0,
    };
  }

  ngOnInit() {
    this.editPostForm = new FormGroup({
      groupId: new FormControl(''),
      title: new FormControl('', Validators.required),
      content: new FormControl('', Validators.required),
      images: new FormControl(''),
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

      this.editPostForm.get('title')?.setValue(data.title);
      this.editPostForm.get('content')?.setValue(this.content);
    });

    this.imageService.getAllImagesByPost(this.postId).subscribe((data) => {
      for (const image of data) {
        this.imagePaths.push(image.path);
        this.imageIds.push(image.id);
      }
      this.editPostForm.get('images')?.setValue(this.imagePaths);
    });
  }

  editPost() {
    this.postPayload.title = this.editPostForm.get('title')?.value;
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

    this.imagePathsNonSeperated = this.editPostForm.get('images')?.value;
    this.seperatedImagePaths = this.imagePathsNonSeperated.split(',');
    for (const path of this.seperatedImagePaths) {
      this.imageModel.path = path;
      this.imageModel.id = this.counter;
      this.counter++;
      this.imageService.editImage(this.imageModel).subscribe();
    }
  }

  discardPost() {
    this.router.navigateByUrl('/');
  }
}
