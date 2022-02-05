import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-knowledge-space-comparison',
  templateUrl: './knowledge-space-comparison.component.html',
  styleUrls: ['./knowledge-space-comparison.component.scss']
})
export class KnowledgeSpaceComparisonComponent implements OnInit {

  id: number | string | null;
  
  constructor(private _ActivatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this._ActivatedRoute.paramMap.subscribe(params => {
      this.id = params.get('id');
    })
  }

}
