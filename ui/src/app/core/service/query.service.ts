import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { JENA_BASE } from '../constants/url.constants';
import { Domain, Result } from '../models';

@Injectable({
  providedIn: 'root'
})
export class QueryService {

  private readonly path = "/sparql"

  constructor(private http: HttpClient) { }

  getQueryResults(queryId: number): Observable<Result> {
    return this.http.get<Result>(`${JENA_BASE}${this.path}/query`, {
        params: {
            id: queryId
        }
    });
  }
}
