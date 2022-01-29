import { Component, ElementRef, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { from } from 'rxjs';
import { Domain, DomainProblem, Edge, KnowledgeSpace } from 'src/app/core/models';
import { DomainService } from 'src/app/core/service/domain.service';
import { KnowledgeSpaceService } from 'src/app/core/service/knowledge-space.service';
import { Data, Network, Node } from 'vis';

@Component({
  selector: 'app-knowledge-space-form',
  templateUrl: './knowledge-space-form.component.html',
  styleUrls: ['./knowledge-space-form.component.scss']
})
export class KnowledgeSpaceFormComponent implements OnInit {

  @ViewChild("spaceConfigNetwork") networkContainer: ElementRef;

  form: FormGroup;
  domainControl: FormControl;
  domains: Domain[];
  space: Network;
  domainProblems: Map<string, DomainProblem>;
  edgesById: string[][] = [];

  @ViewChild('knowledgeSpaceForm')
  public knowledgeSpaceForm!: NgForm;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: KnowledgeSpace,
    public dialogRef: MatDialogRef<KnowledgeSpaceFormComponent>,
    private formBuilder: FormBuilder,
    private knowledgeSpaceService: KnowledgeSpaceService,
    private domainService: DomainService,
    private _snackBar: MatSnackBar) {
    this.domainControl = new FormControl('', Validators.required);
    this.form = this.formBuilder.group({
      text: [data == null ? '' : data.name, Validators.required],
      domainControl: this.domainControl
    });
  }

  ngOnInit(): void {
    this.domainService.getUnassignedDomains().subscribe(
      (data) => {
        this.domains = data;
        if (this.data) {
          this.domains.push(this.data.domain);
          this.domains.sort((x, y) => x.id - y.id);
          this.form.controls['domainControl'].setValue(this.data.domain);
        }
      }
    );
    this.domainProblems = new Map<string, DomainProblem>();
  }

  ngAfterViewInit(): void {
    var graphData = this.getGraphData();
    this.loadGraph(graphData);
  }

  getGraphData(): Data {
    var nodes: Node[] = [];
    var edges: vis.Edge[] = [];

    if (this.data) {
      let nodesMap = new Map<string, Node>();
      this.data.edges.forEach(value => {
        edges.push({
          from: value.from.id,
          to: value.to.id
        });
        this.edgesById.push([value.from.id, value.to.id])
        if (!nodesMap.has(value.to.id)) {
          nodesMap.set(value.to.id, {
            id: value.to.id,
            label: value.to.text
          });
          this.domainProblems.set(value.to.id, value.to);
        }
        if (!nodesMap.has(value.from.id)) {
          nodesMap.set(value.from.id, {
            id: value.from.id,
            label: value.from.text
          });
          this.domainProblems.set(value.from.id, value.from);
        }
      });
      nodes = Array.from<Node>(nodesMap.values());
    }
    return {
      nodes,
      edges
    };
  }

  loadGraph(graphData: Data): void {
    var options = {
      interaction: {
        hover: true
      },
      manipulation: {
        enabled: true,
        addNode: (nodeData: any, callback: any) => {
          callback(nodeData);
          console.log(nodeData);
          this.domainProblems.set(nodeData.id, {
            id: nodeData.id,
            text: nodeData.label
          });
        },
        addEdge: (edgeData: any, callback: any) => {
          callback(edgeData);
          var from = this.domainProblems.get(edgeData.from);
          var to = this.domainProblems.get(edgeData.to);
          if (from && to) {
            this.edgesById.push([
              from.id,
              to.id
            ]);
          }
        }
      },
      edges: {
        arrows: 'to'
      }
    };
    var container = this.networkContainer.nativeElement;
    this.space = new Network(container, graphData, options);

    this.space.on("hoverNode", params => {
    });
    this.space.on("blurNode", params => {
    });
    this.space.on("click", properties => {
    });
  }

  submitKnowledgeSpace() {
    if (this.data == null) {
      this.addKnowledgeSpace();
    } else {
      this.updateKnowledgeSpace();
    }
  }

  updateKnowledgeSpace() {
    const kSpace: KnowledgeSpace = this.getCurrentSpace();
    this.knowledgeSpaceService.updateKnowledgeSpace(kSpace, this.data.id).subscribe(
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

  addKnowledgeSpace() {
    const kSpace: KnowledgeSpace = this.getCurrentSpace();
    this.knowledgeSpaceService.addKnowledgeSpace(kSpace).subscribe(
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

  getCurrentSpace(): KnowledgeSpace {
    var edges: Edge[] = [];
    for (let edge of this.edgesById) {
      var from = this.domainProblems.get(edge[0]);
      var to = this.domainProblems.get(edge[1]);
      if (from && to) {
        edges.push({
          from,
          to
        });
      }
    }
    return {
      id: 0,
      domain: this.form.get("domainControl")?.value,
      isReal: false,
      name: this.form.get("text")?.value,
      edges: edges
    };
  }
} 
