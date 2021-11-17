import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ItemService } from 'src/app/core/service/item.service';

@Component({
  selector: 'app-test-form',
  templateUrl: './test-form.component.html',
  styleUrls: ['./test-form.component.scss'],
})
export class TestFormComponent implements OnInit {
  form: FormGroup;

  @ViewChild('testForm')
  public testForm!: NgForm;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<TestFormComponent>, private formBuilder: FormBuilder, private itemService: ItemService, private _snackBar: MatSnackBar) {
    this.form = this.formBuilder.group({
      name: [data == null ? '' : data.name, Validators.required],
      timer: [data == null ? '' : data.timer, Validators.required],
      validFrom: [data == null ? '' : data.validFrom, Validators.required],
      validUntil: [data == null ? '' : data.validUntil, Validators.required]
    });
  }

  ngOnInit(): void {}

  addTest() {
    const formObj = this.form.getRawValue();
    this.itemService.addTest(formObj).subscribe(
      (data) => {
        console.log(data);
        this.openSnackBar('Test successfuly added!');
        this.dialogRef.close('added');
      },
      (err) => {
        console.log(err);
      }
    )
  }

  openSnackBar(message: string) {
    this._snackBar.open(message, 'Close', {
      duration: 3000
    });
  }
}
