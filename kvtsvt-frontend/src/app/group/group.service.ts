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
    let params = new HttpParams();
    
    if (searchModel.name) {
      params = params.set('name', searchModel.name);
    }
    if (searchModel.description) {
      params = params.set('description', searchModel.description);
    }
    if (searchModel.adminId) {
      params = params.set('adminId', searchModel.adminId.toString());
    }
    if (searchModel.isSuspended !== undefined) {
      params = params.set('isSuspended', searchModel.isSuspended.toString());
    }
    if (searchModel.page) {
      params = params.set('page', searchModel.page.toString());
    }
    if (searchModel.size) {
      params = params.set('size', searchModel.size.toString());
    }

    return this.http.get<Array<GroupModel>>(
      'http://localhost:8080/api/group/search',
      { params }
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
