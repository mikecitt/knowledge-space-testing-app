import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormGroup,
  NgForm,
  Validators,
} from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ItemService } from 'src/app/core/service/item.service';
import { TestFormComponent } from '../test-form/test-form.component';

@Component({
  selector: 'app-item-form',
  templateUrl: './item-form.component.html',
  styleUrls: ['./item-form.component.scss'],
})
export class ItemFormComponent implements OnInit {
  answers = [
    { text: '', points: null },
    { text: '', points: null },
  ];

  count = 2;

  form: FormGroup;

  @ViewChild('testForm')
  private testForm!: NgForm;

  rows: FormArray = this.formBuilder.array(
    this.answers.map((answer) =>
      this.formBuilder.group({
        text: [answer.text],
        points: [answer.points],
      })
    )
  );

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<TestFormComponent>,
    private formBuilder: FormBuilder,
    private itemService: ItemService,
    private _snackBar: MatSnackBar
  ) {
    if (this.data.answers != undefined) this.answers = this.data.answers;
    this.form = this.formBuilder.group({
      text: [data.text, Validators.required],
      answers: this.formBuilder.array(
        this.answers.map((answer) =>
          this.formBuilder.group({
            text: [answer.text],
            points: [answer.points],
          })
        )
      ),
    });
  }

  ngOnInit(): void {
    console.log(this.data);
  }

  removeQuestion(q: number) {
    console.log(q);
    this.rows.removeAt(q);
  }

  addQuestion() {
    // this.answers.push({"text":"a", "points": 5});
    // this.form.get('answers')?.patchValue({"text":"D", "points": 20});
    const row = this.formBuilder.group({
      text: '',
      points: null,
    });
    this.rows.push(row);
    console.log(this.answers);
  }

  addTest() {
    const formObj = this.form.getRawValue();
    console.log(formObj);
    if (this.data.id == undefined) {
      this.itemService.addItem(formObj, this.data.sectionId).subscribe(
        (data) => {
          console.log(data);
          this.openSnackBar('Item successfully added!');
          this.dialogRef.close('added');
        },
        (err) => {
          console.log(err);
        }
      );
    } else {
      this.itemService.updateItem(formObj, this.data.id).subscribe(
        (data) => {
          console.log(data);
          this.openSnackBar('Item successfully updated!');
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

  get answersArray() {
    return (<FormArray>this.form.get('answers')).controls;
  }

  getArrayGroupControlByIndex(index: number) {
    return (<FormArray>this.form.get('answers')).at(index);
  }
}
