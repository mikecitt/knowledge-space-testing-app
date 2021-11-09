import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { ISection } from 'src/app/core/models/section';
import { ItemService } from 'src/app/core/service/item.service';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.scss']
})
export class TestComponent implements OnInit {

  private subs = new Subscription();

  displayedColumns: string[] = ['id', 'name'];

  public dataSource: MatTableDataSource<ISection>;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  private dataArray: any;

  constructor(private _Activatedroute: ActivatedRoute, private itemService: ItemService) { }

  public id: any;

  ngOnInit(): void {
    this.id=this._Activatedroute.snapshot.paramMap.get("id");
    
    this.subs.add(this.itemService.getTestSections(this.id).subscribe(
      (res) => {
        console.log(res);
        this.dataArray = res;
        this.dataSource = new MatTableDataSource<ISection>(this.dataArray);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      (err: HttpErrorResponse) => {
        console.log(err);
      }
    ))
  }

  ngOnDestroy() {
    this.subs.unsubscribe();
  }

}
