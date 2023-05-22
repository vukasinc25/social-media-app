import { PostModel } from './post-model';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  constructor(private http: HttpClient) {}

  getAllPosts(): Observable<Array<PostModel>> {
    return this.http.get<Array<PostModel>>(
      'http://localhost:8080/api/posts/all'
    );
  }

  createPost(postDto: CreatePostDto): Observable<any> {
    return this.http.post('http://localhost:8080/api/post/create', postPayload);
  }

  getPost(id: number): Observable<PostModel> {
    return this.http.get<PostModel>('http://localhost:8080/api/post/' + id);
  }

  getAllPostsByUser(username: string): Observable<PostModel[]> {
    return this.http.get<PostModel[]>(
      'http://localhost:8080/api/post/by-user/' + username
    );
  }
}
