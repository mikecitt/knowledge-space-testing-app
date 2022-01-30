import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Domain } from 'src/app/core/models';
import { DomainService } from 'src/app/core/service/domain.service';
import { ConfirmationDialogComponent } from 'src/app/shared/confirmation-dialog/confirmation-dialog.component';
import { DomainFormComponent } from '../domain-form/domain-form.component';

@Component({
  selector: 'app-domain',
  templateUrl: './domain.component.html',
  styleUrls: ['./domain.component.scss']
})
export class DomainComponent implements OnInit {

  displayedColumns: string[] = [
    'id',
    'name',
    'operation'
  ];

  public dataSource: MatTableDataSource<Domain>;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  private dataArray: any;

  constructor(
    private domainService: DomainService,
    public dialog: MatDialog,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.refresh();
  }

  refresh() {
    this.domainService.getDomains().subscribe(
      (res) => {
        console.log(res);
        this.dataArray = res;
        this.dataSource = new MatTableDataSource<Domain>(this.dataArray);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      (err: HttpErrorResponse) => {
        console.log(err);
      }
    );
  }

  addDomain(): void {
    const dialogRef = this.dialog.open(DomainFormComponent);

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
      this.refresh();
    });
  }

  editDomain(row: Domain): void {
    const dialogRef = this.dialog.open(DomainFormComponent, { data: row });

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
      this.refresh();
    });
  }

  removeDomain(domain: Domain): void {
    this.domainService.deleteDomain(domain.id).subscribe(
      (data) => {
        console.log(data);
        this.refresh();
      },
      (err) => {
        console.log(err);
        this._snackBar.open("This domain contains tests and knowledge spaces", 'Close', {
          duration: 3000,
          panelClass: 'warn-snackbar'
        });
      }
    );
  }

  startDomainDeletion(domain: Domain): void {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      width: 'fit-content',
      data: {
        title: "Deletion", 
        question: "Are you sure you want to delete this domain?" 
      },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.removeDomain(domain);
      }
    });
  }
}
