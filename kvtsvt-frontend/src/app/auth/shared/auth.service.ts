import { PasswordModel } from './../change-password/password-model';
import { LoginRequest } from './../login/login-request-model';
import { RegisterRequestModel } from './../register/register-request-model';
import { LocalStorageService } from 'ngx-webstorage';
import { Injectable, Output, EventEmitter } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { LoginResponse } from '../login/login-response-model';
import { Router } from '@angular/router';
import { SearchModel } from './search-model';
import { UserEditModel } from '../edit-user/user-edit-model';

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
      registerRequestModel
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
          if (!data.isBlocked) {
            console.log('Login sucessfull');

            this.localStorage.store(
              'authenticationToken',
              data.authenticationToken
            );
            this.localStorage.store('userId', data.userId);
            this.localStorage.store('username', data.username);
            this.localStorage.store('expiresAt', data.expiresAt);
            this.localStorage.store('role', data.role);
            this.localStorage.store('isBlocked', data.isBlocked);

            console.log(this.localStorage.retrieve('username'));
            console.log(this.localStorage.retrieve('authenticationToken'));

            console.log('Login Successful');
          } else {
            this.localStorage.store('isBlocked', data.isBlocked);
            console.log('Login blocked');
          }
        })
      );
  }

  changePassword(passwordModel: PasswordModel) {
    return this.httpClient.put(
      'http://localhost:8080/api/user/' + this.localStorage.retrieve('userId'),
      passwordModel
    );
  }

  blockUser(id: number) {
    return this.httpClient.delete('http://localhost:8080/api/user/block/' + id);
  }

  getUser(id: number): Observable<RegisterRequestModel> {
    return this.httpClient.get<RegisterRequestModel>(
      'http://localhost:8080/api/user/' + id
    );
  }

  editUser(user: UserEditModel): Observable<UserEditModel> {
    return this.httpClient.put<UserEditModel>(
      'http://localhost:8080/api/user/edit/' + user.id,
      user
    );
  }

  getUsers(): Observable<Array<RegisterRequestModel>> {
    return this.httpClient.get<Array<RegisterRequestModel>>(
      'http://localhost:8080/api/user/all'
    );
  }

  getBlockedUsers(): Observable<Array<RegisterRequestModel>> {
    return this.httpClient.get<Array<RegisterRequestModel>>(
      'http://localhost:8080/api/user/blocked'
    );
  }

  getUsersFromGroup(groupId: number): Observable<Array<RegisterRequestModel>> {
    return this.httpClient.get<Array<RegisterRequestModel>>(
      'http://localhost:8080/api/user/findUsersGroup/' + groupId
    );
  }

  searchUsers(user: SearchModel): Observable<Array<RegisterRequestModel>> {
    return this.httpClient.post<Array<RegisterRequestModel>>(
      'http://localhost:8080/api/user/search',
      user
    );
  }

  logout() {
    this.localStorage.clear('userId');
    this.localStorage.clear('authenticationToken');
    this.localStorage.clear('username');
    this.localStorage.clear('expiresAt');
    this.localStorage.clear('role');
    this.localStorage.clear('isBlocked');
    this.localStorage.clear('description');
    this.localStorage.clear('newUsername');
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
