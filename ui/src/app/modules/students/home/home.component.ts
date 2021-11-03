import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TestingService } from 'src/app/core/service/testing.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(private router: Router, private testingService: TestingService) { }

  ngOnInit(): void {
  }

  startTest(): void {
    this.testingService.takeTest().subscribe(response => {
      sessionStorage.setItem("takenTest", JSON.stringify(response));
    });
    this.router.navigateByUrl("students/testing");
  }
}
