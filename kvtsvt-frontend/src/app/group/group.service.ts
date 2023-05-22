import { Observable } from 'rxjs';
import { GroupModel } from './group-model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class GroupService {
  constructor() {}

  getAllGroups(): Observable<Array<GroupModel>> {
    return this.http.get<Array<GroupModel>>('http://localhost:8080/group/all');
  }

  createGroup(groupmodel: GroupModel): Observable<GroupModel> {
    return this.http.post<GroupModel>(
      'http://localhost:8080/group/create',
      groupmodel
    );
  }
}
