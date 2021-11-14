import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ItemService } from 'src/app/core/service/item.service';
import { TestFormComponent } from '../test-form/test-form.component';

@Component({
  selector: 'app-item-form',
  templateUrl: './item-form.component.html',
  styleUrls: ['./item-form.component.scss']
})
export class ItemFormComponent implements OnInit {

  count = 2;

  form: FormGroup;

  @ViewChild('testForm')
  private testForm!: NgForm;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<TestFormComponent>, private formBuilder: FormBuilder, private itemService: ItemService, private _snackBar: MatSnackBar) {
    this.form = this.formBuilder.group({
      text: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    console.log(this.data);
  }

  addTest() {
    const formObj = this.form.getRawValue();
    this.itemService.addItem(formObj, this.data.sectionId).subscribe(
      (data) => {
        console.log(data);
        this.openSnackBar('Section successfuly added!');
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

  test() {
    console.log(this.count);
  }

}
