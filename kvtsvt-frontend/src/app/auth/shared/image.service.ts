import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ImageModel } from './image-model';

@Injectable({
  providedIn: 'root',
})
export class ImageService {
  constructor(private http: HttpClient) {}

  getAllImagesByPost(id: number): Observable<Array<ImageModel>> {
    return this.http.get<Array<ImageModel>>(
      'http://localhost:8080/api/image/byPost/' + id
    );
  }

  getImageByUser(id: number): Observable<ImageModel> {
    return this.http.get<ImageModel>(
      'http://localhost:8080/api/image/byUser/' + id
    );
  }

  getImage(id: number): Observable<ImageModel> {
    return this.http.get<ImageModel>('http://localhost:8080/api/image/' + id);
  }

  createImage(imageModel: ImageModel): Observable<ImageModel> {
    return this.http.post<ImageModel>(
      'http://localhost:8080/api/image/create',
      imageModel
    );
  }

  editImage(imageModel: ImageModel): Observable<ImageModel> {
    return this.http.put<ImageModel>(
      'http://localhost:8080/api/image/' + imageModel.id,
      imageModel
    );
  }

  deleteImage(id: number): Observable<any> {
    return this.http.delete<any>('http://localhost:8080/api/image/' + id);
  }
}
