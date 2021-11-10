import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ItemService } from 'src/app/core/service/item.service';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.scss'],
})
export class TestComponent implements OnInit {
  public sections: any = null;
  public test: any = null;

  constructor(
    private _Activatedroute: ActivatedRoute,
    private itemService: ItemService
  ) {}

  public id: any;

  ngOnInit(): void {
    this.id = this._Activatedroute.snapshot.paramMap.get('id');

    this.itemService.getTest(this.id).subscribe(
      (res) => {
        console.log(res);
        this.test = res;
      },
      (err) => {
        console.log(err);
      }
    )

    this.itemService.getTestSections(this.id).subscribe(
      (res) => {
        console.log(res);
        this.sections = res;
      },
      (err) => {
        console.log(err);
      }
    );
  }
}
