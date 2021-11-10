import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ITest } from 'src/app/core/models/test';
import { ItemService } from 'src/app/core/service/item.service';
import { TestFormComponent } from '../test-form/test-form.component';

@Component({
  selector: 'app-tests-table',
  templateUrl: './tests-table.component.html',
  styleUrls: ['./tests-table.component.scss'],
})
export class TestsTableComponent implements OnInit {
  displayedColumns: string[] = [
    'id',
    'name',
    'timer',
    'validFrom',
    'validUntil',
    'action',
  ];

  public dataSource: MatTableDataSource<ITest>;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  private dataArray: any;

  constructor(
    private itemService: ItemService,
    private router: Router,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.refresh();
  }

  refresh() {
    this.itemService.getTests().subscribe(
      (res) => {
        console.log(res);
        this.dataArray = res;
        this.dataSource = new MatTableDataSource<ITest>(this.dataArray);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      (err: HttpErrorResponse) => {
        console.log(err);
      }
    );
  }

  addTest() {
    const dialogRef = this.dialog.open(TestFormComponent);

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
      this.refresh();
    });
  }

  openTest(id: number) {
    this.router.navigateByUrl(`professors/test/${id}`);
  }
}
