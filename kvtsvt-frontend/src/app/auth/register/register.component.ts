import { ImageModel } from './../shared/image-model';
import { RegisterRequestModel } from './register-request-model';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../shared/auth.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { ImageService } from '../shared/image.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  registerRequestModel!: RegisterRequestModel;
  ImageModel: ImageModel;

  constructor(
    private authService: AuthService,
    private router: Router,
    private toastr: ToastrService,
    private imageService: ImageService
  ) {
    this.ImageModel = {
      id: 0,
      path: '',
      postId: 0,
      userId: 0,
    };
    this.registerRequestModel = {
      id: 0,
      email: '',
      username: '',
      firstname: '',
      lastname: '',
      password: '',
    };
  }

  ngOnInit() {
    this.registerForm = new FormGroup({
      email: new FormControl('', Validators.required),
      username: new FormControl('', Validators.required),
      firstname: new FormControl('', Validators.required),
      lastname: new FormControl('', Validators.required),
      profileImagePath: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
  }

  register() {
    this.registerRequestModel.email = this.registerForm.get('email')?.value;
    this.registerRequestModel.username =
      this.registerForm.get('username')?.value;
    this.registerRequestModel.firstname =
      this.registerForm.get('firstname')?.value;
    this.registerRequestModel.lastname =
      this.registerForm.get('lastname')?.value;
    this.registerRequestModel.password =
      this.registerForm.get('password')?.value;

    this.ImageModel.path = this.registerForm.get('profileImagePath')?.value;

    this.authService.register(this.registerRequestModel).subscribe(
      (data) => {
        this.ImageModel.userId = data.id;
        console.log(data.id);
        console.log('sadasdsadasdasd');
        this.imageService.createImage(this.ImageModel).subscribe();
        this.router.navigate(['/login'], {
          queryParams: { registered: 'true' },
        });
      },
      () => {
        this.toastr.error('Registration failed');
      }
    );
  }
}
