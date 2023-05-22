import { CommentPayload } from './comment-payload';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root',
})
export class CommentService {
  constructor(private httpClient: HttpClient) {}

  getAllCommentsForPost(postId: number): Observable<CommentPayload[]> {
    return this.httpClient.get<CommentPayload[]>(
      'http://localhost:8080/api/comment/byPost/' + postId
    );
  }

  postComment(commentPayload: CommentPayload): Observable<any> {
    return this.httpClient.post<any>(
      'http://localhost:8080/api/comment/create',
      commentPayload
    );
  }

  getAllCommentsByUser(name: string) {
    return this.httpClient.get<CommentPayload[]>(
      'http://localhost:8080/api/comments/by-user/' + name
    );
  }
}
