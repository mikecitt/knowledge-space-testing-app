import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { ItemService } from 'src/app/core/service/item.service';
import { SectionFormComponent } from '../section-form/section-form.component';

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
    private itemService: ItemService,
    public dialog: MatDialog
  ) {}

  public id: any;

  ngOnInit(): void {
    this.id = this._Activatedroute.snapshot.paramMap.get('id');

    this.loadTest();
    this.loadSections();
  }

  loadTest() {
    this.itemService.getTest(this.id).subscribe(
      (res) => {
        console.log(res);
        this.test = res;
      },
      (err) => {
        console.log(err);
      }
    )
  }

  loadSections() {
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

  checkDatePassed(date: Date) {
    return true;
  }

  addSection() {
    const dialogRef = this.dialog.open(SectionFormComponent, {data: {testId: this.id}});

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
      this.loadSections();
    });
  }
}
