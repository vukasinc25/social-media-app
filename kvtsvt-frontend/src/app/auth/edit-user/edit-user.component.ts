import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { throwError } from 'rxjs';
import { GroupService } from 'src/app/group/group.service';
import { CommentPayload } from 'src/app/post/comment/comment-payload';
import { CommentService } from 'src/app/post/comment/comment.service';
import { AuthService } from '../shared/auth.service';
import { RegisterRequestModel } from '../register/register-request-model';
import { UserEditModel } from './user-edit-model';
import { LocalStorageService } from 'ngx-webstorage';
import { ImageService } from '../shared/image.service';
import { ImageModel } from '../shared/image-model';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css'],
})
export class EditUserComponent {
  editUserForm!: FormGroup;
  user: UserEditModel;
  imageModel: ImageModel;
  userId: number = 0;
  text: string = 'a';
  image: string = '';
  imageId: number = 0;

  constructor(
    private router: Router,
    private groupService: GroupService,
    private activateRoute: ActivatedRoute,
    private authService: AuthService,
    private localStorage: LocalStorageService,
    private imageService: ImageService
  ) {
    this.userId = this.activateRoute.snapshot.params['id'];
    this.user = {
      id: 0,
      firstname: '',
      lastname: '',
      email: '',
    };
    this.imageModel = {
      id: 0,
      path: '',
    };
    this.imageService.getImageByUser(this.userId).subscribe((data) => {
      this.image = data.path;
      this.imageId = data.id;
    });
  }

  ngOnInit() {
    this.editUserForm = new FormGroup({
      firstname: new FormControl('', Validators.required),
      lastname: new FormControl('', Validators.required),
      email: new FormControl('', Validators.required),
      picture: new FormControl('', Validators.required),
      newUsername: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
    });

    this.authService.getUser(this.userId).subscribe((data) => {
      this.user.id = data.id;
      this.editUserForm.get('firstname')?.setValue(data.firstname);
      this.editUserForm.get('lastname')?.setValue(data.lastname);
      this.editUserForm.get('email')?.setValue(data.email);
      this.editUserForm.get('picture')?.setValue(this.image);
      this.editUserForm
        .get('newUsername')
        ?.setValue(this.localStorage.retrieve('newUsername'));
      this.editUserForm
        .get('description')
        ?.setValue(this.localStorage.retrieve('description'));
    });
  }

  editUser() {
    this.user.firstname = this.editUserForm.get('firstname')?.value;
    this.user.lastname = this.editUserForm.get('lastname')?.value;
    this.user.email = this.editUserForm.get('email')?.value;

    this.imageModel.id = this.imageId;
    this.imageModel.path = this.editUserForm.get('picture')?.value;
    console.log(this.imageModel.path);

    this.imageService.editImage(this.imageModel).subscribe();

    this.authService.editUser(this.user).subscribe(
      (data) => {
        this.router.navigateByUrl('/user-profile/' + this.user.id);
      },
      (error) => {
        throwError(error);
      }
    );

    this.localStorage.store(
      'newUsername',
      this.editUserForm.get('newUsername')?.value
    );
    this.localStorage.store(
      'description',
      this.editUserForm.get('description')?.value
    );
  }

  discardUser() {
    this.router.navigateByUrl('/');
  }
}
