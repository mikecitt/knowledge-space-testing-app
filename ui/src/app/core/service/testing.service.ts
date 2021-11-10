import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_BASE } from '../constants/url.constants';

@Injectable({
  providedIn: 'root'
})
export class TestingService {

  private readonly path = "/test"
  private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  takeTest(): Observable<any> {
    return this.http.get(`${API_BASE}${this.path}/start`, {
      params: {
        studentId: 1000,
        testId: 1
      }
    });
  }

  getNextQuestion(currentItem: any): Observable<any> {
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
      
    return this.http.get(`${API_BASE}${this.path}/question/next`, {
      params: params
    });
  }
}
