import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { API_BASE } from '../constants/url.constants';
import { ItemProblem, PagedEntity } from '../models';
import { ITest } from '../models/test';

@Injectable({
  providedIn: 'root',
})
export class ItemService {
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) {}

  getTests(): Observable<ITest[]> {
    return this.http.get<ITest[]>(`${API_BASE}/test/`).pipe(
      map((resp) => {
        resp.forEach((test) => {
          test.validFrom = new Date(test.validFrom);
          test.validUntil = new Date(test.validUntil);
        });
        return resp;
      })
    );
  }

  searchTests(
    searchKeyword: string,
    pageSize: number,
    page: number
  ): Observable<PagedEntity<ITest>> {
    return this.http
      .patch<PagedEntity<ITest>>(
        `${API_BASE}/test`,
        {},
        {
          params: {
            searchKeyword: searchKeyword,
            page: page,
            size: pageSize,
          },
        }
      )
      .pipe(
        map((resp) => {
          resp.content.forEach((test) => {
            test.validFrom = new Date(test.validFrom);
            test.validUntil = new Date(test.validUntil);
          });
          return resp;
        })
      );
  }

  getTest(id: any) {
    return this.http.get<any>(`${API_BASE}/test/${id}`).pipe(
      map((resp) => {
        resp.validFrom = new Date(resp.validFrom);
        resp.validUntil = new Date(resp.validUntil);
        return resp;
      })
    );
  }

  addTest(test: any) {
    return this.http.post<any>(`${API_BASE}/test/`, test);
  }

  removeTest(id: any) {
    return this.http.delete<any>(`${API_BASE}/test/${id}`);
  }

  updateTest(test: any, id: any) {
    return this.http.put<any>(`${API_BASE}/test/${id}`, test);
  }

  addSection(section: any, testId: number) {
    return this.http.post<any>(
      `${API_BASE}/section/add?testId=${testId}`,
      section
    );
  }

  updateSection(section: any, id: any) {
    return this.http.put<any>(`${API_BASE}/section/${id}`, section);
  }

  removeSection(id: any) {
    return this.http.delete<any>(`${API_BASE}/section/${id}`);
  }

  addItem(item: any, sectionId: number) {
    return this.http.post<any>(`${API_BASE}/item?sectionId=${sectionId}`, item);
  }

  updateItem(item: any, id: any) {
    return this.http.put<any>(`${API_BASE}/item/${id}`, item);
  }

  removeItem(id: any) {
    return this.http.delete<any>(`${API_BASE}/item/${id}`);
  }

  getTestSections(testId: number): Observable<ITest> {
    return this.http.get<ITest>(`${API_BASE}/section/`, {
      params: {
        testId: testId,
      },
    });
  }

  updateTestDomain(testId: number, domainId: number): Observable<void> {
    return this.http.put<void>(`${API_BASE}/test/${testId}/domain-update?domainId=${domainId}`, {});
  }

  assignItemsToKSpace(itemProblems: ItemProblem[]): Observable<void> {
    return this.http.put<void>(`${API_BASE}/test/assign-items`, itemProblems);
  }
}
