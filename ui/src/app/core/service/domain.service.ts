import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { API_BASE } from '../constants/url.constants';
import { Domain } from '../models';

@Injectable({
  providedIn: 'root'
})
export class DomainService {

  private readonly path = "/domain"
  private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  getDomains(): Observable<Domain[]> {
    return this.http.get<Domain[]>(`${API_BASE}/domain`).pipe(
        map((resp) => {
          return resp;
        })
      );
  }

  getUnassignedDomains(): Observable<any[]> {
    return this.http.get<any[]>(`${API_BASE}/domain/unassigned`).pipe(
      map((resp) => {
        return resp;
      })
    );
  }

  addDomain(domain: Domain): Observable<Domain> {
    return this.http.post<Domain>(`${API_BASE}/domain/`, domain);
  }

  updateDomain(domain: Domain, domainId: number): Observable<Domain> {
    return this.http.put<Domain>(`${API_BASE}/domain/${domainId}`, domain);
  }

  deleteDomain(domainId: number): Observable<void> {
    return this.http.delete<void>(`${API_BASE}/domain/${domainId}`);
  }
  
  getDomainKnowledgeSpaceComparison(domainId: any): Observable<any> {
    return this.http.get<any[]>(`${API_BASE}/domain/knowledge-space-comparison/${domainId}`);
  }
}
