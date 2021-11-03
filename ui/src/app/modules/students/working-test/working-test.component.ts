import { Component, OnInit } from '@angular/core';
import { TestingService } from 'src/app/core/service/testing.service';

@Component({
  selector: 'app-working-test',
  templateUrl: './working-test.component.html',
  styleUrls: ['./working-test.component.scss']
})
export class WorkingTestComponent implements OnInit {

  public currentItem: any = null;
  public loading: boolean = false;

  constructor(private testingService: TestingService) { }

  ngOnInit(): void {
    this.testingService.getNextQuestion(this.currentItem)
      .subscribe(
        response => {
          this.currentItem = response;
          console.log(this.currentItem)
        },
        error => {
          console.log(error)
        });
  }

  nextQuestion(): void {
    this.loading = true;
    this.testingService.getNextQuestion(this.currentItem)
      .subscribe(
        response => {
          setTimeout(() => {
            this.loading = false;
            this.currentItem = response;
          }, 500)
        },
        error => {
          console.log(error);
          this.loading = false;
        })
  }
}
