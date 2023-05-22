import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ReactionModel } from './reaction-button/reaction-model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ReactionService {
  constructor(private http: HttpClient) {}

  vote(reactionModel: ReactionModel): Observable<any> {
    return this.http.post('http://localhost:8080/api/reaction', reactionModel);
  }
}
