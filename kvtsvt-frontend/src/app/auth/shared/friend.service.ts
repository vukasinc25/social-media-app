import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FriendRequestModel } from './friend-request-model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FriendService {
  constructor(private http: HttpClient) {}

  getAllFriendRequests(): Observable<Array<FriendRequestModel>> {
    return this.http.get<Array<FriendRequestModel>>(
      'http://localhost:8080/api/friendRequest/all'
    );
  }

  getFriendRequest(id: number): Observable<FriendRequestModel> {
    return this.http.get<FriendRequestModel>(
      'http://localhost:8080/api/friendRequest/' + id
    );
  }

  createFriendRequest(
    friendRequestmodel: FriendRequestModel
  ): Observable<FriendRequestModel> {
    return this.http.post<FriendRequestModel>(
      'http://localhost:8080/api/friendRequest/create',
      friendRequestmodel
    );
  }

  editFriendRequest(id: number): Observable<any> {
    return this.http.delete<any>(
      'http://localhost:8080/api/friendRequest/accept/' + id
    );
  }

  deleteFriendRequest(id: number): Observable<any> {
    return this.http.delete<any>(
      'http://localhost:8080/api/friendRequest/delete/' + id
    );
  }
}
