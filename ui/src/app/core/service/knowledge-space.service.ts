import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { API_BASE } from '../constants/url.constants';
import { ItemProblem, KnowledgeSpace } from '../models';

@Injectable({
  providedIn: 'root'
})
export class KnowledgeSpaceService {

  private readonly path = "/knowledge-space"
  private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  getKnowledgeSpaces(domainId: number = -1): Observable<KnowledgeSpace[]> {
    let requestParam = '';
    if (domainId !== -1) {
      requestParam = `?domainId=${domainId}`;
    }
    return this.http.get<KnowledgeSpace[]>(`${API_BASE}/knowledge-space${requestParam}`).pipe(
        map((resp) => {
          return resp;
        })
      );
  }

  addKnowledgeSpace(knowledgeSpace: KnowledgeSpace): Observable<KnowledgeSpace> {
    return this.http.post<KnowledgeSpace>(`${API_BASE}/knowledge-space/`, knowledgeSpace);
  }

  updateKnowledgeSpace(knowledgeSpace: KnowledgeSpace, knowledgSpaceId: number): Observable<KnowledgeSpace> {
    return this.http.put<KnowledgeSpace>(`${API_BASE}/knowledge-space/${knowledgSpaceId}`, knowledgeSpace);
  }

  deleteKnowledgeSpace(knowledgeSpaceId: number): Observable<void> {
    return this.http.delete<void>(`${API_BASE}/knowledge-space/${knowledgeSpaceId}`);
  }

  getAssignedProblems(kSpaceId: number, testId: number): Observable<ItemProblem[]> {
    return this.http.get<ItemProblem[]>(`${API_BASE}/knowledge-space/assigned-problems`, { 
      params: {
        kSpaceId,
        testId
      }
    });
  }
}
