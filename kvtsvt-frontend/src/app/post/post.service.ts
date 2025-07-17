import { CreatePostDto } from './create-post-dto';
import { PostModel } from './post-model';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
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

  searchPosts(searchModel: PostSearchModel): Observable<Array<PostModel>> {
    let params = new HttpParams();
    
    if (searchModel.title) {
      params = params.set('title', searchModel.title);
    }
    if (searchModel.content) {
      params = params.set('content', searchModel.content);
    }
    if (searchModel.userId) {
      params = params.set('userId', searchModel.userId.toString());
    }
    if (searchModel.groupId) {
      params = params.set('groupId', searchModel.groupId.toString());
    }
    if (searchModel.startDate) {
      params = params.set('startDate', searchModel.startDate.toISOString());
    }
    if (searchModel.endDate) {
      params = params.set('endDate', searchModel.endDate.toISOString());
    }
    if (searchModel.sortBy) {
      params = params.set('sortBy', searchModel.sortBy);
    }
    if (searchModel.page) {
      params = params.set('page', searchModel.page.toString());
    }
    if (searchModel.size) {
      params = params.set('size', searchModel.size.toString());
    }

    return this.http.get<Array<PostModel>>(
      'http://localhost:8080/api/post/search',
      { params }
    );
  }

  createPost(postDto: CreatePostDto): Observable<any> {
    return this.http.post('http://localhost:8080/api/post/create', postDto);
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
