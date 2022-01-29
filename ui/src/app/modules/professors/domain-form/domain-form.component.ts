import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DomainService } from 'src/app/core/service/domain.service';

@Component({
  selector: 'app-domain-form',
  templateUrl: './domain-form.component.html',
  styleUrls: ['./domain-form.component.scss']
})
export class DomainFormComponent implements OnInit {
  form: FormGroup;

  @ViewChild('domainForm')
  public domainForm!: NgForm;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<DomainFormComponent>, private formBuilder: FormBuilder, private domainService: DomainService, private _snackBar: MatSnackBar) {
    this.form = this.formBuilder.group({
      text: [data == null ? '' : data.text, Validators.required]
    });
  }

  ngOnInit(): void { }

  submitDomain() {
    if (this.data == null) {
      this.addDomain();
    } else {
      this.updateDomain();
    }
  }

  updateDomain() {
    const formObj = this.form.getRawValue();
    this.domainService.updateDomain(formObj, this.data.id).subscribe(
      (data) => {
        console.log(data);
        this.openSnackBar('Domain successfuly updated!');
        this.dialogRef.close('added');
      },
      (err) => {
        console.log(err);
      }
    )
  }

  addDomain() {
    const formObj = this.form.getRawValue();
    this.domainService.addDomain(formObj).subscribe(
      (data) => {
        console.log(data);
        this.openSnackBar('Domain successfuly added!');
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
