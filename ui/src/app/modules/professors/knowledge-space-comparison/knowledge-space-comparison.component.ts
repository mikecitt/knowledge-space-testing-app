import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DomainService } from 'src/app/core/service/domain.service';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-knowledge-space-comparison',
  templateUrl: './knowledge-space-comparison.component.html',
  styleUrls: ['./knowledge-space-comparison.component.scss']
})
export class KnowledgeSpaceComparisonComponent implements OnInit {

  center$: Subject<boolean> = new Subject();

  id: number | string | null;
  edges: any;
  nodes: any;
  color = "blue";
  
  constructor(private _ActivatedRoute: ActivatedRoute, private domainService: DomainService) { }

  ngOnInit(): void {
    this._ActivatedRoute.paramMap.subscribe(params => {
      this.id = params.get('id');
      this.domainService.getDomainKnowledgeSpaceComparison(this.id).subscribe(
        (res) => {
          this.edges = res.edges;
          this.nodes = res.nodes;
          console.log(this.nodes);
        }
      )
    });
  }

}
