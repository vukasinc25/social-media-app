import { Observable } from 'rxjs';
import { GroupModel } from './group-model';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GroupAdminModel } from './group-admin-model';
import { GroupSearchModel } from './search-group/search-group-model';

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

  getGroup(id: number): Observable<GroupModel> {
    return this.http.get<GroupModel>('http://localhost:8080/api/group/' + id);
  }

  searchGroups(searchModel: GroupSearchModel): Observable<Array<GroupModel>> {
    return this.http.post<Array<GroupModel>>(
      'http://localhost:8080/api/search/groups/combined',
       searchModel 
    );
  }

  createGroup(groupmodel: GroupModel, pdfFile?: File): Observable<GroupModel> {
    const formData = new FormData();
    formData.append('group', new Blob([JSON.stringify(groupmodel)], { type: 'application/json' }));
    if (pdfFile) {
      formData.append('pdfFile', pdfFile);
    }
    return this.http.post<GroupModel>('http://localhost:8080/api/group/create', formData);
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

  getAllGroupAdmins(): Observable<GroupAdminModel[]> {
    return this.http.get<GroupAdminModel[]>(
      'http://localhost:8080/api/groupAdmin'
    );
  }

  removeGroupAdmin(id: number) {
    return this.http.delete('http://localhost:8080/api/groupAdmin/' + id);
  }

  deleteGroup(groupModel: GroupModel) {
    return this.http.put('http://localhost:8080/api/group/delete', groupModel);
  }
}
