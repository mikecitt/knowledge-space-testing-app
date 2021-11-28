import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { API_BASE } from '../constants/url.constants';
import { WorkingTest, Item, TakenTest, PagedEntity } from '../models';
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

  getNextQuestion(currentItem: Item | null): Observable<Item> {
    var temp = sessionStorage.getItem("takenTest");
    if(!temp) {
      throw "There is no taken test at the time";
    } 

    var currentTest = JSON.parse(temp);
    var params: any = {
      workingTestId: currentTest.id
    };
    if(currentItem)
      params.itemId = currentItem.id;
      
    return this.http.get<Item>(`${API_BASE}${this.path}/question/next`, {
      params: params
    });
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
