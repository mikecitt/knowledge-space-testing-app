import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { ITest } from 'src/app/core/models';
import { TestingService } from 'src/app/core/service/testing.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  public loading: boolean = false;

  constructor(private router: Router, private testingService: TestingService, private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
  }

  startTest(test: ITest): void {
    this.loading = true;
    this.testingService.takeTest(test.id).subscribe(
      response => {
        setTimeout(() => {
          this.loading = false;
          this.router.navigateByUrl("students/testing");
        }, 500)
      },
      error => {
        setTimeout(() => {
          this.loading = false;
          this.openSnackBar('You can only have one taken test at the time.');
        }, 500)
      });
  }

  openSnackBar(message: string) {
    this._snackBar.open(message, '', {
      duration: 3000
    });
  }
}
