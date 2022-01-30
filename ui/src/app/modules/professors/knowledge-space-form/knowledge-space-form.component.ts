import { Component, ElementRef, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Domain, DomainProblem, Edge, KnowledgeSpace } from 'src/app/core/models';
import { DomainService } from 'src/app/core/service/domain.service';
import { KnowledgeSpaceService } from 'src/app/core/service/knowledge-space.service';
import { Data, DataSet, Network, Node } from 'vis';


@Component({
  selector: 'app-knowledge-space-form',
  templateUrl: './knowledge-space-form.component.html',
  styleUrls: ['./knowledge-space-form.component.scss']
})
export class KnowledgeSpaceFormComponent implements OnInit {

  @ViewChild("spaceConfigNetwork") networkContainer: ElementRef;

  form: FormGroup;
  domainControl: FormControl;
  domainProblemName: FormControl;
  domains: Domain[];
  space: Network;
  domainProblems: Map<string, DomainProblem>;
  edgesById: string[][] = [];
  graphData: Data;
  currentNodeId: string;

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
    this.domainProblemName = new FormControl('', Validators.required);
    this.domainProblemName.disable();
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
    this.graphData = this.getGraphData();
    this.loadGraph(this.graphData);
  }

  getGraphData(): Data {
    var nodes: Node[] = [];
    var edges: vis.Edge[] = [];

    if (this.data) {
      let nodesMap = new Map<string, Node>();
      this.data.edges.forEach(value => {
        edges.push({
          id: `${value.from.id}-${value.to.id}`,
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
      nodes: new DataSet(nodes),
      edges: new DataSet(edges)
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
          this.domainProblems.set(nodeData.id, {
            id: nodeData.id,
            text: nodeData.label
          });
        },
        deleteNode: (nodeData: any, callback: any) => {
          callback(nodeData);
          this.edgesById = this.edgesById
            .filter(x => nodeData.edges[0] !== `${x[0]}-${x[1]}`);
          for (let node of nodeData.nodes) {
            this.domainProblems.delete(node);
          }
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
        },
        deleteEdge: (edgeData: any, callback: any) => {
          callback(edgeData);
          this.edgesById = this.edgesById
            .filter(x => edgeData.edges[0] !== `${x[0]}-${x[1]}`);
        },
        editEdge: false
      },
      edges: {
        arrows: 'to'
      }
    };
    var container = this.networkContainer.nativeElement;
    this.space = new Network(container, graphData, options);

    this.space.on("click", properties => {
      if (properties.nodes.length !== 0) {
        var node = this.domainProblems.get(properties.nodes[0]);
        this.currentNodeId = properties.nodes[0];
        this.domainProblemName.setValue(node?.text);
        this.domainProblemName.enable();
      } else {
        this.domainProblemName.setValue('');
        this.domainProblemName.disable();
      }
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
        this.openSnackBar('Knowledge space successfuly updated!');
        this.dialogRef.close('added');
      },
      (err) => {
        console.log(err);
        this.openSnackBar('Knowledge space was not updated, something is wrong with your form.', 'warn-snackbar');
      }
    )
  }

  addKnowledgeSpace() {
    const kSpace: KnowledgeSpace = this.getCurrentSpace();
    this.knowledgeSpaceService.addKnowledgeSpace(kSpace).subscribe(
      (data) => {
        this.openSnackBar('Knowledge space successfuly added!');
        this.dialogRef.close('added');
      },
      (err) => {
        console.log(err);
        this.openSnackBar('Knowledge space was not updated, something is wrong with your form.', 'warn-snackbar');
      }
    )
  }

  openSnackBar(message: string, panelClass: string = '') {
    this._snackBar.open(message, 'Close', {
      duration: 3000,
      panelClass: panelClass
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

  problemNameUpdate(event: Event) {
    console.log(event);
    console.log(this.domainProblemName.value);
    var domainProblem = this.domainProblems.get(this.currentNodeId);
    if (this.graphData.nodes instanceof DataSet && domainProblem && this.domainProblemName.value) {
      domainProblem.text = this.domainProblemName.value;
      this.graphData.nodes.update({
        id: this.currentNodeId,
        label: domainProblem.text
      });
    }
  }
} 
