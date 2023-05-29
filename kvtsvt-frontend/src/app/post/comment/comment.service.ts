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

  getAllCommentsForPostDesc(postId: number): Observable<CommentPayload[]> {
    return this.httpClient.get<CommentPayload[]>(
      'http://localhost:8080/api/comment/byPostDesc/' + postId
    );
  }

  getCommentsByReaction(id: number): Observable<CommentPayload[]> {
    return this.httpClient.get<CommentPayload[]>(
      'http://localhost:8080/api/comment/byPostReaction/' + id
    );
  }

  getComment(id: number): Observable<CommentPayload> {
    return this.httpClient.get<CommentPayload>(
      'http://localhost:8080/api/comment/' + id
    );
  }

  getCommentChildren(id: number): Observable<Array<CommentPayload>> {
    return this.httpClient.get<Array<CommentPayload>>(
      'http://localhost:8080/api/comment/all/' + id
    );
  }

  updateComment(comment: CommentPayload): Observable<CommentPayload> {
    return this.httpClient.put<CommentPayload>(
      'http://localhost:8080/api/comment/' + comment.id,
      comment
    );
  }

  deleteComment(id: number): Observable<CommentPayload> {
    return this.httpClient.delete<CommentPayload>(
      'http://localhost:8080/api/comment/' + id
    );
  }

  postComment(commentPayload: CommentPayload): Observable<any> {
    return this.httpClient.post<any>(
      'http://localhost:8080/api/comment/create',
      commentPayload
    );
  }

  getAllCommentsByUser(id: number) {
    return this.httpClient.get<CommentPayload[]>(
      'http://localhost:8080/api/comment/byUser/' + id
    );
  }
}
