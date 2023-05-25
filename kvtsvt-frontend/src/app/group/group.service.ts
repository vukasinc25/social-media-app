import { Observable } from 'rxjs';
import { GroupModel } from './group-model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GroupAdminModel } from './group-admin-model';

@Injectable({
  providedIn: 'root',
})
export class GroupService {
  constructor(private http: HttpClient) {}

  getAllGroups(): Observable<Array<GroupModel>> {
    return this.http.get<Array<GroupModel>>(
      'http://localhost:8080/api/group/all'
    );
  }

  createGroup(groupmodel: GroupModel): Observable<GroupModel> {
    return this.http.post<GroupModel>(
      'http://localhost:8080/api/group/create',
      groupmodel
    );
  }

  editGroup(groupModel: GroupModel): Observable<any> {
    return this.http.put<any>(
      'http://localhost:8080/api/group/' + groupModel.id,
      groupModel
    );
  }

  createGroupAdmin(
    groupAdminModel: GroupAdminModel
  ): Observable<GroupAdminModel> {
    return this.http.post<GroupAdminModel>(
      'http://localhost:8080/api/groupAdmin/create',
      groupAdminModel
    );
  }

  deleteGroup(id: number) {
    return this.http.delete('http://localhost:8080/api/group/' + id);
  }
}
