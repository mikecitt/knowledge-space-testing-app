import { Component, OnInit } from '@angular/core';
import { ItemAnswers, StudentItem } from 'src/app/core/models';
import { AuthService } from 'src/app/core/service/auth.service';
import { TestingService } from 'src/app/core/service/testing.service';
import { IMG_BASE } from 'src/app/core/constants/url.constants';

@Component({
  selector: 'app-working-test',
  templateUrl: './working-test.component.html',
  styleUrls: ['./working-test.component.scss']
})
export class WorkingTestComponent implements OnInit {

  public currentItem: StudentItem | null = null;
  public loading: boolean = false;
  public IMG_BASE = IMG_BASE;

  constructor(private testingService: TestingService, private authService: AuthService) { }

  ngOnInit(): void {
    try {
      this.testingService.getNextQuestion(this.currentItem)
        .subscribe(
          response => {
            this.currentItem = response;
            if (this.currentItem == null) {
              this.authService.getRouter().navigate(['/students']);
            }
          },
          error => {
            this.authService.getRouter().navigate(['/students']);
          });
    } catch (err) {
      console.log(err);
      this.authService.getRouter().navigate(['/students']);
    }
  }

  return(): void {
    this.authService.getRouter().navigate(['/students']);
  }

  nextQuestion(): void {
    this.loading = true;
    if (this.currentItem) {
      var currItem = this.currentItem;
      var itemAnswers: ItemAnswers = {
        itemId: currItem.id,
        selectedAnswers: this.currentItem.answers
          .filter(elem => elem.selected)
          .map(elem => elem.id)
      };
      this.testingService.answerOnQuestion(itemAnswers)
        .subscribe(response => {
          this.testingService.getNextQuestion(this.currentItem)
            .subscribe(
              response => {
                setTimeout(() => {
                  this.loading = false;
                  this.currentItem = response;
                  if (this.currentItem == null) {
                    this.authService.getRouter().navigate(['/students']);
                  }
                }, 500)
              },
              error => {
                console.log(error);
                this.loading = false;
              })
        });
    }
  }
}
