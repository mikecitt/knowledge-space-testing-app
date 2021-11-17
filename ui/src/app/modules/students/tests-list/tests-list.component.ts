import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { PageEvent } from '@angular/material/paginator';
import { ITest } from 'src/app/core/models';
import { ItemService } from 'src/app/core/service/item.service';

@Component({
  selector: 'app-tests-list',
  templateUrl: './tests-list.component.html',
  styleUrls: ['./tests-list.component.scss']
})
export class TestsListComponent implements OnInit {

  page: number;
  pageSize: number;
  length: number;
  tests: ITest[]
  searchKeyword = new FormControl('');
  doneTypingInterval = 800; // in ms
  typingTimer: any;
  searchIconCode = 'search';

  constructor(private testService: ItemService) { }

  ngOnInit(): void {
    var page = sessionStorage.getItem("currentTestPage");
    var pageSize = sessionStorage.getItem("currentTestPageSize");
    var searchKeyword = sessionStorage.getItem("currentTestSearch");
    this.page = page ? parseInt(page) : 0;
    this.pageSize = pageSize ? parseInt(pageSize) : 10;
    this.searchKeyword.setValue(searchKeyword ? searchKeyword : '');
    this.checkIcon();
    this.searchTests();
  }

  searchTests(): void {
    this.testService.searchTests(this.searchKeyword.value, this.pageSize, this.page).subscribe(
      res => {
        this.tests = res.content;
        this.page = res.currentPage;
        this.pageSize = res.pageSize;
        this.length = res.totalElements;
        sessionStorage.setItem("currentTestPage", this.page.toString());
        sessionStorage.setItem("currentTestPageSize", this.pageSize.toString());
        sessionStorage.setItem("currentTestSearch", this.searchKeyword.value);
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
}
