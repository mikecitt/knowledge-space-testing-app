import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { ItemService } from 'src/app/core/service/item.service';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { ItemFormComponent } from '../item-form/item-form.component';
import { SectionFormComponent } from '../section-form/section-form.component';
import { TestFormComponent } from '../test-form/test-form.component';

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
    public dialog: MatDialog,
    private router: Router
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
    );
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

  editTest() {
    const dialogRef = this.dialog.open(TestFormComponent, { data: this.test });

    dialogRef.afterClosed().subscribe((result) => {
      if (result != true) this.loadTest();
    });
  }

  removeTest() {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        message: 'Are you sure want to delete?',
        buttonText: {
          ok: 'Yes',
          cancel: 'No',
        },
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
      if (result === true) {
        this.itemService.removeTest(this.id).subscribe(
          (res) => {
            console.log(res);
            this.router.navigateByUrl(`/professors`);
          },
          (err) => {
            console.log(err);
          }
        );
      }
    });
  }

  addSection() {
    const dialogRef = this.dialog.open(SectionFormComponent, {
      data: { testId: this.id },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
      if (result != true) this.loadSections();
    });
  }

  updateSection(section: any) {
    const dialogRef = this.dialog.open(SectionFormComponent, { data: section });

    dialogRef.afterClosed().subscribe((result) => {
      if (result != true) this.loadSections();
    });
  }

  removeSection(section: any) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        message: 'Are you sure want to delete?',
        buttonText: {
          ok: 'Yes',
          cancel: 'No',
        },
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
      if (result === true) {
        this.itemService.removeSection(section.id).subscribe(
          (res) => {
            console.log(res);
            this.loadSections();
            // this.router.navigateByUrl(`/professors`);
          },
          (err) => {
            console.log(err);
          }
        );
      }
    });
  }

  addItem(section: any) {
    const dialogRef = this.dialog.open(ItemFormComponent, {
      data: { sectionId: section.id },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
      if (result == 'added') this.loadSections();
    });
  }

  updateItem(item: any) {
    const dialogRef = this.dialog.open(ItemFormComponent, {
      data: item,
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
      if (result == 'added') this.loadSections();
    });
  }

  removeItem(item: any) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        message: 'Are you sure want to delete?',
        buttonText: {
          ok: 'Yes',
          cancel: 'No',
        },
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
      if (result === true) {
        this.itemService.removeItem(item.id).subscribe(
          (res) => {
            console.log(res);
            this.loadSections();
            // this.router.navigateByUrl(`/professors`);
          },
          (err) => {
            console.log(err);
          }
        );
      }
    });
  }
}
