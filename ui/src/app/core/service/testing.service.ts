import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { API_BASE } from '../constants/url.constants';
import { WorkingTest, TakenTest, PagedEntity, ItemAnswers, StudentItem } from '../models';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class TestingService {

  private readonly path = "/test"
  private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient, private authService: AuthService) { }

  takeTest(testId: number): Observable<WorkingTest> {
    return this.http.get<WorkingTest>(`${API_BASE}${this.path}/start`, {
      params: {
        testId: testId
      }
    });
  }

  getNextQuestion(currentItem: StudentItem | null): Observable<StudentItem> {
    var params: any = {};
    if(currentItem)
      params.itemId = currentItem.id;
      
    return this.http.get<StudentItem>(`${API_BASE}${this.path}/question/next`, {
      params: params
    });
  }

  answerOnQuestion(itemAnswers: ItemAnswers): Observable<void> {
    return this.http.post<void>(`${API_BASE}${this.path}/taken/answer`, itemAnswers);
  }

  getCurrentWorkingTest(): Observable<WorkingTest> {
    return this.http.get<WorkingTest>(`${API_BASE}${this.path}/taken/current`);
  }

  searchTakenTests(searchKeyword: string, pageSize: number, page: number): Observable<PagedEntity<TakenTest>> {
    return this.http.patch<PagedEntity<TakenTest>>(`${API_BASE}${this.path}/taken`, {}, {
      params: {
        searchKeyword: searchKeyword,
        page: page,
        size: pageSize
      }
    }).pipe(map(resp => {
      resp.content.forEach(test => {
        test.start = new Date(test.start);
        test.end = test.end ? new Date(test.end) : null;
      });
      return resp;
    }));;
  }
}
