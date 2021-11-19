import { Component, EventEmitter, OnInit, Output, ViewEncapsulation } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { ITest } from 'src/app/core/models';
import { ItemService } from 'src/app/core/service/item.service';
import { ConfirmationDialogComponent } from 'src/app/shared/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-tests-list',
  templateUrl: './tests-list.component.html',
  styleUrls: ['./tests-list.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class TestsListComponent implements OnInit {

  page: number;
  pageSize: number;
  length: number;
  tests: ITest[]
  expanded: boolean[] = [];
  searchKeyword = new FormControl('');
  doneTypingInterval = 800; // in ms
  typingTimer: any;
  searchIconCode = 'search';

  @Output() onTestStart = new EventEmitter<ITest>();

  constructor(private testService: ItemService, public dialog: MatDialog) { }

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

        this.expanded = [];
        for (let i = 0; i < this.tests.length; i++)
          this.expanded.push(false);
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

  expandCard(testIndex: number): void {
    for(let i = 0; i < this.expanded.length; i++) {
      if (i != testIndex) {
        this.expanded[i] = false;
      }
    }
    this.expanded[testIndex] = !this.expanded[testIndex]
  }

  showStartTestDialog(test: ITest): void {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      width: 'fit-content',
      data: {
        title: "Starting test", 
        question: "Are you sure you want to take this test now?" 
      },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.onTestStart.emit(test);
      }
    });
  }
}
