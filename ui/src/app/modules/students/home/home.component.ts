import { Component, OnInit } from '@angular/core';
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

  constructor(private router: Router, private testingService: TestingService) { }

  ngOnInit(): void {
  }

  startTest(test: ITest): void {
    this.loading = true;
    this.testingService.takeTest(test.id).subscribe(
      response => {
        sessionStorage.setItem("takenTest", JSON.stringify(response));
        setTimeout(() => {
          this.loading = false;
          this.router.navigateByUrl("students/testing");
        }, 500)
      },
      error => {
        this.loading = false;
      });
  }
}
