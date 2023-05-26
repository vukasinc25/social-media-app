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
    return this.http.post(
      'http://localhost:8080/api/reaction/create',
      reactionModel
    );
  }

  getAllReactionsByPost(id: number): Observable<Array<ReactionModel>> {
    return this.http.get<Array<ReactionModel>>(
      'http://localhost:8080/api/reaction/byPost/' + id
    );
  }

  getAllReactionsByComment(id: number): Observable<ReactionModel[]> {
    return this.http.get<ReactionModel[]>(
      'http://localhost:8080/api/reaction/byComment/' + id
    );
  }

  createReaction(reactionModel: ReactionModel): Observable<any> {
    return this.http.post(
      'http://localhost:8080/api/reaction/create',
      reactionModel
    );
  }

  updateReaction(reactionModel: ReactionModel): Observable<any> {
    return this.http.put(
      'http://localhost:8080/api/reaction/' + reactionModel.id,
      reactionModel
    );
  }

  getReaction(id: number): Observable<ReactionModel> {
    return this.http.get<ReactionModel>(
      'http://localhost:8080/api/reaction/' + id
    );
  }

  deleteReaction(postId: number) {
    return this.http.delete('http://localhost:8080/api/reaction/' + postId);
  }
}
