import { AuthService } from './../shared/auth.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { LoginRequest } from './login-request-model';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { throwError } from 'rxjs';
import { LocalStorageService } from 'ngx-webstorage';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  loginRequestModel!: LoginRequest;
  registerSuccessMessage!: string;
  isError!: boolean;

  constructor(
    private authService: AuthService,
    private router: Router,
    private toastr: ToastrService,
    private activatedRoute: ActivatedRoute,
    private localStorageService: LocalStorageService
  ) {
    this.loginRequestModel = {
      username: '',
      password: '',
    };
  }

  ngOnInit() {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
    // this.activatedRoute.queryParams.subscribe((params) => {
    //   if (params['registered'] !== undefined && params['registered'] === true) {
    //     this.toastr.success('Registration successful');
    //     this.registerSuccessMessage = 'Registration successful';
    //   }
    // });
  }

  login() {
    this.loginRequestModel.username = this.loginForm.get('username')?.value;
    this.loginRequestModel.password = this.loginForm.get('password')?.value;

    this.authService.login(this.loginRequestModel).subscribe(
      (data) => {
        if (this.localStorageService.retrieve('isBlocked')) {
          this.isError = true;
          this.toastr.error('User is blocked');
        } else {
          this.toastr.success('Login successful');
          this.router.navigate(['/']);
        }
      },
      (error) => {
        this.isError = true;
        throwError(error);
      }
    );
  }
}
