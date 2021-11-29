import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { PageEvent } from '@angular/material/paginator';
import { TakenTest } from 'src/app/core/models';
import { TestingService } from 'src/app/core/service/testing.service';

@Component({
  selector: 'app-taken-test-list',
  templateUrl: './taken-test-list.component.html',
  styleUrls: ['./taken-test-list.component.scss']
})
export class TakenTestListComponent implements OnInit {

  page: number;
  pageSize: number;
  length: number;
  tests: TakenTest[]
  searchKeyword = new FormControl('');
  doneTypingInterval = 800; // in ms
  typingTimer: any;
  searchIconCode = 'search';
  remainingTimeValue: number = -1;

  constructor(private testService: TestingService) { }

  ngOnInit(): void {
    var page = sessionStorage.getItem("currentTakenTestPage");
    var pageSize = sessionStorage.getItem("currentTakenTestPageSize");
    var searchKeyword = sessionStorage.getItem("currentTakenTestSearch");
    this.page = page ? parseInt(page) : 0;
    this.pageSize = pageSize ? parseInt(pageSize) : 10;
    this.searchKeyword.setValue(searchKeyword ? searchKeyword : '');
    this.checkIcon();
    this.searchTests();
  }

  remainingTime(test: TakenTest): number {
    if (this.remainingTimeValue === -1)
      this.remainingTimeValue = (test.start.getTime() + test.testDuration * 60 * 1000 - Date.now()) / 1000;
    return this.remainingTimeValue;
  }

  searchTests(): void {
    this.testService.searchTakenTests(this.searchKeyword.value, this.pageSize, this.page).subscribe(
      res => {
        this.remainingTimeValue = -1;
        this.tests = res.content;
        this.page = res.currentPage;
        this.pageSize = res.pageSize;
        this.length = res.totalElements;
        sessionStorage.setItem("currentTakenTestPage", this.page.toString());
        sessionStorage.setItem("currentTakenTestPageSize", this.pageSize.toString());
        sessionStorage.setItem("currentTakenTestSearch", this.searchKeyword.value);
      }
    )
  }

  onPaginateChange(event: PageEvent): void {
    this.page = event.pageIndex;
    this.pageSize = event.pageSize;
    this.searchTests();
  }

  resetSearch(): void {
    this.searchIconCode = 'search';
    this.searchKeyword.setValue('');
    this.searchTests();
  }

  checkIcon(): void {
    if (this.searchKeyword.value !== "") {
      this.searchIconCode = 'cancel';
    } else {
      this.searchIconCode = 'search';
    }
  }

  keyUpEvent(): void {
    clearTimeout(this.typingTimer);
    this.typingTimer = setTimeout(() => this.searchTests(), this.doneTypingInterval);
  }

  keyDownEvent(): void {
    clearTimeout(this.typingTimer);
  }

  takeBackToTest(): void {
    
  }
}
