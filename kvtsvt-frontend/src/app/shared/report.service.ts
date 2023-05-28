import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ReportModel } from './create-report/report-model';

@Injectable({
  providedIn: 'root',
})
export class ReportService {
  constructor(private http: HttpClient) {}

  vote(reportModel: ReportModel): Observable<any> {
    return this.http.post(
      'http://localhost:8080/api/report/create',
      reportModel
    );
  }

  getAllReportsByPost(id: number): Observable<Array<ReportModel>> {
    return this.http.get<Array<ReportModel>>(
      'http://localhost:8080/api/report/byPost/' + id
    );
  }

  getAllReportsByComment(id: number): Observable<ReportModel[]> {
    return this.http.get<ReportModel[]>(
      'http://localhost:8080/api/report/byComment/' + id
    );
  }

  createReport(reportModel: ReportModel): Observable<any> {
    return this.http.post(
      'http://localhost:8080/api/report/create',
      reportModel
    );
  }

  updateReport(reportModel: ReportModel): Observable<any> {
    return this.http.put(
      'http://localhost:8080/api/report/' + reportModel.id,
      reportModel
    );
  }

  getReport(id: number): Observable<ReportModel> {
    return this.http.get<ReportModel>('http://localhost:8080/api/report/' + id);
  }

  deleteReport(reportModel: ReportModel): Observable<any> {
    return this.http.put(
      'http://localhost:8080/api/report/delete',
      reportModel
    );
  }
}
