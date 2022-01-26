import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { KnowledgeSpace } from 'src/app/core/models';
import { KnowledgeSpaceService } from 'src/app/core/service/knowledge-space.service';

@Component({
  selector: 'app-knowledge-space',
  templateUrl: './knowledge-space.component.html',
  styleUrls: ['./knowledge-space.component.scss']
})
export class KnowledgeSpaceComponent implements OnInit {

  displayedColumns: string[] = [
    'id',
    'name',
    'domain',
    'isReal',
    'operation'
  ];

  public dataSource: MatTableDataSource<KnowledgeSpace>;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  private dataArray: any;

  constructor(
    private knowledgeSpace: KnowledgeSpaceService,
    public dialog: MatDialog,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.refresh();
  }

  refresh() {
    this.knowledgeSpace.getKnowledgeSpaces().subscribe(
      (res) => {
        console.log(res);
        this.dataArray = res;
        this.dataSource = new MatTableDataSource<KnowledgeSpace>(this.dataArray);
        this.dataSource.sortingDataAccessor = (item, property) => {
          switch (property) {
             case 'id': return item.id;
             case 'name': return item.name;
             case 'domain': return item.domain.text;
             case 'isReal': return item.isReal ? "Yes" : "No";
             default: return '';
          }
        };
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      (err: HttpErrorResponse) => {
        console.log(err);
      }
    );
  }

  /*addDomain(): void {
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
          panelClass: 'blue-snackbar'
        });
      }
    );
  }*/
}
