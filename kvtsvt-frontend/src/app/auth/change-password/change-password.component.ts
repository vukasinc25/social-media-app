import { AuthService } from './../shared/auth.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { throwError } from 'rxjs';
import { LoginRequest } from '../login/login-request-model';
import { PasswordModel } from './password-model';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css'],
})
export class ChangePasswordComponent implements OnInit {
  changePasswordForm!: FormGroup;
  passwordModel!: PasswordModel;
  isError!: boolean;
  passwordOld: string = '';
  password: string = '';
  passwordRepeat: string = '';

  constructor(
    private authService: AuthService,
    private router: Router,
    private toastr: ToastrService,
    private activatedRoute: ActivatedRoute
  ) {
    this.passwordModel = {
      passwordOld: '',
      password: '',
    };
  }

  ngOnInit() {
    this.changePasswordForm = new FormGroup({
      passwordOld: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
      passwordRepeat: new FormControl('', Validators.required),
    });
  }

  changePassword() {
    this.passwordOld = this.changePasswordForm.get('passwordOld')?.value;
    this.password = this.changePasswordForm.get('password')?.value;
    this.passwordRepeat = this.changePasswordForm.get('passwordRepeat')?.value;

    if (this.password == this.passwordRepeat) {
      this.passwordModel.passwordOld = this.passwordOld;
      this.passwordModel.password = this.password;

      this.authService.changePassword(this.passwordModel).subscribe(
        (data) => {
          this.isError = false;
          this.router.navigateByUrl('');
          this.toastr.success('Password updated successfully');
        },
        (error) => {
          this.isError = true;
          throwError(error);
        }
      );
    } else {
      console.log(' not maching');
    }
  }
}
