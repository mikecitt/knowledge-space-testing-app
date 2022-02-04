import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatSelectChange } from '@angular/material/select';
import { Result } from 'src/app/core/models';
import { QueryService } from 'src/app/core/service/query.service';

interface Query {
  name: string,
  id: number
}

@Component({
  selector: 'app-query',
  templateUrl: './query.component.html',
  styleUrls: ['./query.component.scss']
})
export class QueryComponent implements OnInit {

  queries: Query[] = [
    {
      name: 'Test scores',
      id: 1
    },
    {
      name: 'Tests per course',
      id: 2
    },
    {
      name: 'Average test duration',
      id: 3
    },
    {
      name: 'Number of courses per professor',
      id: 4
    }
  ];

  queryControl: FormControl;
  data: Result;

  constructor(private queryService: QueryService, private datePipe: DatePipe) {
    this.queryControl = new FormControl('');
  }

  ngOnInit(): void {
    this.queryService.getQueryResults(1).subscribe(
      res => {
        this.data = res;
      },
      error => {
        console.log(error);
      }
    );
  }

  onChange(source: MatSelectChange): void {
    console.log(source);
    this.queryService.getQueryResults(source.value.id).subscribe(
      res => {
        this.data = res;
      },
      error => {
        console.log(error);
      }
    );
  }

  capitalize(str: string): string {
    let newStr = str.split('_').join(' ');
    return newStr[0].toUpperCase() + newStr.slice(1);
  }

  format(val: any, colName: string): string {
    let retVal = val;
    if (colName == 'average_duration') {
      let date = new Date(2000, 1, 1, 0, 0, val);
      retVal = this.datePipe.transform(date, "HH:mm:ss");
    }
    return retVal + "";
  }
}
