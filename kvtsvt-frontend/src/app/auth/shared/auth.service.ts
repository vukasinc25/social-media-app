import { PasswordModel } from './../change-password/password-model';
import { LoginRequest } from './../login/login-request-model';
import { RegisterRequestModel } from './../register/register-request-model';
import { LocalStorageService } from 'ngx-webstorage';
import { Injectable, Output, EventEmitter } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { LoginResponse } from '../login/login-response-model';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  @Output() loggedIn: EventEmitter<boolean> = new EventEmitter();
  @Output() username: EventEmitter<string> = new EventEmitter();

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
          console.log('Login sucessfull');

          this.localStorage.store(
            'authenticationToken',
            data.authenticationToken
          );
          this.localStorage.store('userId', data.userId);
          this.localStorage.store('username', data.username);
          this.localStorage.store('expiresAt', data.expiresAt);
          this.localStorage.store('role', data.role);

          console.log(this.localStorage.retrieve('userId'));
          console.log(this.localStorage.retrieve('username'));
          console.log(this.localStorage.retrieve('authenticationToken'));
          console.log(this.localStorage.retrieve('expiresAt'));
          console.log(this.localStorage.retrieve('role'));
        })
      );
  }

  changePassword(passwordModel: PasswordModel) {
    return this.httpClient.put(
      'http://localhost:8080/api/user/' + this.localStorage.retrieve('userId'),
      passwordModel
    );
  }

  getUser(id: number): Observable<RegisterRequestModel> {
    return this.httpClient.get<RegisterRequestModel>(
      'http://localhost:8080/api/user/' + id
    );
  }

  logout() {
    this.localStorage.clear('userId');
    this.localStorage.clear('authenticationToken');
    this.localStorage.clear('username');
    this.localStorage.clear('expiresAt');
  }

  tokenIsPresent() {
    return (
      this.localStorage.retrieve('authenticationToken') != undefined &&
      this.localStorage.retrieve('authenticationToken') != null
    );
  }

  getUserName() {
    return this.localStorage.retrieve('username');
  }

  getUserId() {
    return this.localStorage.retrieve('userId');
  }

  getJwtToken() {
    // console.log(this.localStorage.retrieve('authenticationToken'));
    return this.localStorage.retrieve('authenticationToken');
  }

  isLoggedIn(): boolean {
    // console.log(this.getJwtToken());
    return this.getJwtToken() != null;
  }
}
