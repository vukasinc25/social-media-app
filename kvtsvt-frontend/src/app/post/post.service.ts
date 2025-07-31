import { CreatePostDto } from './create-post-dto';
import { PostModel } from './post-model';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PostSearchModel } from './search-post/search-post-model';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  constructor(private http: HttpClient) {}

  getAllPosts(): Observable<Array<PostModel>> {
    return this.http.get<Array<PostModel>>(
      'http://localhost:8080/api/post/all'
    );
  }

  getAllPostsDesc(): Observable<Array<PostModel>> {
    return this.http.get<Array<PostModel>>(
      'http://localhost:8080/api/post/allDesc'
    );
  }

  getAllPostsByUser(id: number): Observable<PostModel[]> {
    return this.http.get<PostModel[]>(
      'http://localhost:8080/api/post/byUser/' + id
    );
  }

  getAllPostsByGroup(id: number): Observable<PostModel[]> {
    return this.http.get<PostModel[]>(
      'http://localhost:8080/api/post/byGroup/' + id
    );
  }

  searchPosts(searchModel: PostSearchModel): Observable<any> {
    return this.http.post(
      'http://localhost:8080/api/search/posts/combined',
      searchModel
    );
  }
 
  createPost(postDto: CreatePostDto, pdfFile?: File): Observable<any> {
    const formData = new FormData();
    formData.append('post', new Blob([JSON.stringify(postDto)], { type: 'application/json' }));
    if (pdfFile) {
      formData.append('pdfFile', pdfFile);
    }
    return this.http.post('http://localhost:8080/api/post/create', formData);
  }

  updatePost(postDto: CreatePostDto): Observable<any> {
    return this.http.put(
      'http://localhost:8080/api/post/' + postDto.id,
      postDto
    );
  }

  getPost(id: number): Observable<PostModel> {
    return this.http.get<PostModel>('http://localhost:8080/api/post/' + id);
  }

  deletePost(postId: number) {
    return this.http.delete('http://localhost:8080/api/post/' + postId);
  }
}
