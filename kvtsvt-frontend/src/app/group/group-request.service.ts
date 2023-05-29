import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GroupAdminModel } from './group-admin-model';
import { GroupRequestModel } from './group-request-model';
import { Observable } from 'rxjs';
import { group } from '@angular/animations';

@Injectable({
  providedIn: 'root',
})
export class GroupRequestService {
  constructor(private http: HttpClient) {}

  getAllRequests(id: number): Observable<Array<GroupRequestModel>> {
    return this.http.get<Array<GroupRequestModel>>(
      'http://localhost:8080/api/groupRequest/all/' + id
    );
  }

  getRequest(id: number): Observable<GroupRequestModel> {
    return this.http.get<GroupRequestModel>(
      'http://localhost:8080/api/groupRequest/' + id
    );
  }

  createGroupRequest(
    groupRequest: GroupRequestModel
  ): Observable<GroupRequestModel> {
    return this.http.post<GroupRequestModel>(
      'http://localhost:8080/api/groupRequest/create',
      groupRequest
    );
  }

  approveGR(groupReustModel: GroupRequestModel): Observable<any> {
    return this.http.put<any>(
      'http://localhost:8080/api/groupRequest/' + groupReustModel.id,
      groupReustModel
    );
  }

  banGR(groupRequestModel: GroupRequestModel) {
    return this.http.put(
      'http://localhost:8080/api/groupRequest/ban/' + groupRequestModel.id,
      groupRequestModel
    );
  }

  deleteGroup(id: number) {
    return this.http.delete('http://localhost:8080/api/groupRequest/' + id);
  }
}
