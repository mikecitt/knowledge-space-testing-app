import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_BASE } from '../constants/url.constants';
import { ITest } from '../models/test';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  getTests(): Observable<ITest> {
    return this.http.get<ITest>(`${API_BASE}/test/`);
  }

  getTest(id: any) {
    return this.http.get<any>(`${API_BASE}/test/${id}`);
  }

  addTest(test: any) {
    return this.http.post<any>(`${API_BASE}/test/`, test);
  }

  addSection(section: any, testId: number) {
    return this.http.post<any>(`${API_BASE}/section/add?testId=${testId}`, section);
  }

  getTestSections(testId: number): Observable<ITest> {
    return this.http.get<ITest>(`${API_BASE}/section/`, {
      params: {
        testId: testId
      }
    });
  }
}
