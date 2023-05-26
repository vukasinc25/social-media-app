import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GroupAdminModel } from './group-admin-model';
import { GroupRequestModel } from './group-request-model';
import { Observable } from 'rxjs';

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

  editGroup(groupReustModel: GroupRequestModel): Observable<any> {
    return this.http.put<any>(
      'http://localhost:8080/api/groupRequest/' + groupReustModel.id,
      groupReustModel
    );
  }

  deleteGroup(id: number) {
    return this.http.delete('http://localhost:8080/api/groupRequest/' + id);
  }
}
