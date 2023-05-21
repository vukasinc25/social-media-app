import { LoginRequest } from './../login/login-request-model';
import { RegisterRequestModel } from './../register/register-request-model';
import { LocalStorageService } from 'ngx-webstorage';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { LoginResponse } from '../login/login-response-model';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(
    private httpClient: HttpClient,
    private localStorage: LocalStorageService,
    private router: Router
  ) {}

  register(registerRequestModel: RegisterRequestModel): Observable<any> {
    return this.httpClient.post(
      'http://localhost:8080/api/user/signup',
      registerRequestModel,
      { responseType: 'text' }
    );
  }

  login(loginRequestModel: LoginRequest): Observable<any> {
    ``;
    return this.httpClient
      .post<LoginResponse>(
        'http://localhost:8080/api/user/login',
        loginRequestModel
      )
      .pipe(
        map((data) => {
          console.log('Login sucessfulf');
          this.localStorage.store(
            'authenticationToken',
            data.authenticationToken
          );
          this.localStorage.store('username', data.username);
          this.localStorage.store('refreshToken', data.refreshToken);
          this.localStorage.store('expiresAt', data.expiresAt);
        })
      );
  }

  logout() {
    // this.userService.currentUser = null;
    this.localStorage.retrieve('authenticationToken');
    this.router.navigate(['/login']);
  }

  tokenIsPresent() {
    return (
      this.localStorage.retrieve('authenticationToken') != undefined &&
      this.localStorage.retrieve('authenticationToken') != null
    );
  }

  getJwtToken() {
    return this.localStorage.retrieve('authenticationToken');
  }
}
