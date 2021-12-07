import { Component, Inject, Input, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ItemService } from 'src/app/core/service/item.service';
import { TestFormComponent } from '../test-form/test-form.component';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-section-form',
  templateUrl: './section-form.component.html',
  styleUrls: ['./section-form.component.scss'],
})
export class SectionFormComponent implements OnInit {
  form: FormGroup;

  @ViewChild('testForm')
  private testForm!: NgForm;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<TestFormComponent>,
    private formBuilder: FormBuilder,
    private itemService: ItemService,
    private _snackBar: MatSnackBar
  ) {
    this.form = this.formBuilder.group({
      name: [data.name, Validators.required],
    });
  }

  ngOnInit(): void {
    console.log(this.data);
  }

  addTest() {
    const formObj = this.form.getRawValue();
    if (this.data.testId == undefined) {
      this.itemService.addSection(formObj, this.data.testId).subscribe(
        (data) => {
          console.log(data);
          this.openSnackBar('Section successfuly added!');
          this.dialogRef.close('added');
        },
        (err) => {
          console.log(err);
        }
      );
    } else {
      this.itemService.updateSection(formObj, this.data.id).subscribe(
        (data) => {
          console.log(data);
          this.openSnackBar('Section successfuly updated!');
          this.dialogRef.close('added');
        },
        (err) => {
          console.log(err);
        }
      );
    }
  }

  openSnackBar(message: string) {
    this._snackBar.open(message, 'Close', {
      duration: 3000,
    });
  }
}
