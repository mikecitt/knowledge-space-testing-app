import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_BASE } from '../constants/url.constants';
import { PagedEntity } from '../models';
import { ITest } from '../models/test';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) { }

  getTests(): Observable<ITest> {
    return this.http.get<ITest>(`${API_BASE}/test/`);
  }

  searchTests(searchKeyword: string, pageSize: number, page: number): Observable<PagedEntity<ITest>> {
    return this.http.patch<PagedEntity<ITest>>(`${API_BASE}/test`, {}, {
      params: {
        searchKeyword: searchKeyword,
        page: page,
        size: pageSize
      }
    });
  }

  getTest(id: any) {
    return this.http.get<any>(`${API_BASE}/test/${id}`);
  }

  addTest(test: any) {
    return this.http.post<any>(`${API_BASE}/test/`, test);
  }

  removeTest(id: any) {
    return this.http.delete<any>(`${API_BASE}/test/${id}`);
  }

  addSection(section: any, testId: number) {
    return this.http.post<any>(`${API_BASE}/section/add?testId=${testId}`, section);
  }

  removeSection(id: any) {
    return this.http.delete<any>(`${API_BASE}/section/${id}`);
  }

  addItem(item: any, sectionId: number) {
    return this.http.post<any>(`${API_BASE}/item?sectionId=${sectionId}`, item);
  }
  
  removeItem(id: any) {
    return this.http.delete<any>(`${API_BASE}/item/${id}`);
  }

  getTestSections(testId: number): Observable<ITest> {
    return this.http.get<ITest>(`${API_BASE}/section/`, {
      params: {
        testId: testId
      }
    });
  }
}
