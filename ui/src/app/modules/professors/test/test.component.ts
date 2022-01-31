import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSelectChange } from '@angular/material/select';
import { ActivatedRoute, Router } from '@angular/router';
import { Domain, DomainProblem, Item, ItemProblem, KnowledgeSpace } from 'src/app/core/models';
import { DomainService } from 'src/app/core/service/domain.service';
import { ItemService } from 'src/app/core/service/item.service';
import { KnowledgeSpaceService } from 'src/app/core/service/knowledge-space.service';
import { Data, DataSet, Network, Node } from 'vis';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { ItemFormComponent } from '../item-form/item-form.component';
import { SectionFormComponent } from '../section-form/section-form.component';
import { TestFormComponent } from '../test-form/test-form.component';
import { IMG_BASE } from 'src/app/core/constants/url.constants';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.scss'],
})
export class TestComponent implements OnInit {

  @ViewChild("spaceConfigNetwork") networkContainer: ElementRef;

  public sections: any = null;
  public test: any = null;
  public IMG_BASE = IMG_BASE;
  domainControl: FormControl;
  itemControl: FormControl;
  domains: Domain[];
  graphData: Data;
  space: Network;
  domainProblems: Map<string, DomainProblem>;
  itemProblems: ItemProblem[];
  selectedProblemId: string;
  allItems: any[];

  constructor(
    private _Activatedroute: ActivatedRoute,
    private itemService: ItemService,
    private domainService: DomainService,
    private knowledgeSpaceService: KnowledgeSpaceService,
    public dialog: MatDialog,
    private router: Router
  ) {
    this.domainControl = new FormControl('', Validators.required);
    this.itemControl = new FormControl('');
    this.itemControl.disable();
  }

  public id: any;

  ngOnInit(): void {
    this.id = this._Activatedroute.snapshot.paramMap.get('id');
    this.domainProblems = new Map<string, DomainProblem>();
    this.allItems = [];

    this.loadTest();
    this.loadSections();
  }

  loadKnowledgeSpace(domainId: number): void {
    this.knowledgeSpaceService.getKnowledgeSpaces(domainId)
      .subscribe(data => {
        if (data.length != 0) {
          let space = data.find(x => x.isReal);
          if (space) {
            this.graphData = this.getGraphData(space);
          } else {
            space = data[0];
            this.graphData = this.getGraphData(data[0]);
          }
          this.loadItemProblems(space.id, this.test.id);
        } else {
          this.graphData = {};
        }
        this.loadGraph(this.graphData);
      });
  }

  loadItemProblems(kSpaceId: number, testId: number): void {
    this.knowledgeSpaceService.getAssignedProblems(kSpaceId, testId)
      .subscribe(data => {
        this.itemProblems = data;
        console.log(this.itemProblems);
      })
  }

  getGraphData(data: KnowledgeSpace): Data {
    var nodes: Node[] = [];
    var edges: vis.Edge[] = [];

    if (data) {
      let nodesMap = new Map<string, Node>();
      data.edges.forEach(value => {
        edges.push({
          id: `${value.from.id}-${value.to.id}`,
          from: value.from.id,
          to: value.to.id
        });
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
        enabled: false
      },
      edges: {
        arrows: 'to'
      }
    };
    var container = this.networkContainer.nativeElement;
    this.space = new Network(container, graphData, options);

    this.space.on("click", properties => {
      if (properties.nodes.length !== 0) {
        this.selectedProblemId = properties.nodes[0];
        let itemProblem = this.itemProblems.find(x => x.domainProblemId == this.selectedProblemId);
        let item = this.allItems.find(x => x.id === itemProblem?.itemId);

        if (item) {
          this.itemControl.setValue(item);
        }
        else {
          this.itemControl.setValue("");
        }
        this.itemControl.enable();
      } else {
        this.itemControl.setValue('');
        this.itemControl.disable();
        this.selectedProblemId = "";
      }
    });
  }

  loadDomains(): void {
    this.domainService.getDomains().subscribe(
      (data) => {
        this.domains = data;
        if (this.test?.domain != null) {
          let domain = this.domains.find(x => this.test.domain.id == x.id);
          this.domainControl.setValue(domain);
        }
      }
    );
  }

  loadTest() {
    this.itemService.getTest(this.id).subscribe(
      (res) => {
        console.log(res);
        this.test = res;
        this.loadDomains();
        this.loadKnowledgeSpace(this.test.domain.id);
      },
      (err) => {
        console.log(err);
      }
    );
  }

  loadSections() {
    this.itemService.getTestSections(this.id).subscribe(
      (res) => {
        console.log(res);
        this.sections = res;
        for(let section of this.sections) {
          this.allItems.push(...section.items);
        }
        console.log(this.allItems);
      },
      (err) => {
        console.log(err);
      }
    );
  }

  checkDatePassed(date: Date) {
    return date.getTime() <= new Date().getTime();
  }

  editTest() {
    const dialogRef = this.dialog.open(TestFormComponent, { data: this.test });

    dialogRef.afterClosed().subscribe((result) => {
      if (result != true) this.loadTest();
    });
  }

  removeTest() {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        message: 'Are you sure want to delete?',
        buttonText: {
          ok: 'Yes',
          cancel: 'No',
        },
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
      if (result === true) {
        this.itemService.removeTest(this.id).subscribe(
          (res) => {
            console.log(res);
            this.router.navigateByUrl(`/professors`);
          },
          (err) => {
            console.log(err);
          }
        );
      }
    });
  }

  addSection() {
    const dialogRef = this.dialog.open(SectionFormComponent, {
      data: { testId: this.id },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
      if (result != true) this.loadSections();
    });
  }

  updateSection(section: any) {
    const dialogRef = this.dialog.open(SectionFormComponent, { data: section });

    dialogRef.afterClosed().subscribe((result) => {
      if (result != true) this.loadSections();
    });
  }

  removeSection(section: any) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        message: 'Are you sure want to delete?',
        buttonText: {
          ok: 'Yes',
          cancel: 'No',
        },
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
      if (result === true) {
        this.itemService.removeSection(section.id).subscribe(
          (res) => {
            console.log(res);
            this.loadSections();
            // this.router.navigateByUrl(`/professors`);
          },
          (err) => {
            console.log(err);
          }
        );
      }
    });
  }

  addItem(section: any) {
    const dialogRef = this.dialog.open(ItemFormComponent, {
      data: { sectionId: section.id },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
      if (result == 'added') this.loadSections();
    });
  }

  updateItem(item: any) {
    const dialogRef = this.dialog.open(ItemFormComponent, {
      data: item,
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
      if (result == 'added') this.loadSections();
    });
  }

  onSpaceChange(source: MatSelectChange): void {
    this.loadKnowledgeSpace(source.value.id);
  }

  onItemChange(source: MatSelectChange): void {
    for (let itemProblem of this.itemProblems) {
      if (itemProblem.domainProblemId == this.selectedProblemId) {
        itemProblem.itemId = source.value.id;
        continue;
      }
      if (itemProblem.itemId == source.value.id) {
        itemProblem.itemId = -1;
      }
    }

    console.log(this.itemProblems);
  }

  removeItem(item: any) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        message: 'Are you sure want to delete?',
        buttonText: {
          ok: 'Yes',
          cancel: 'No',
        },
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
      if (result === true) {
        this.itemService.removeItem(item.id).subscribe(
          (res) => {
            console.log(res);
            this.loadSections();
            // this.router.navigateByUrl(`/professors`);
          },
          (err) => {
            console.log(err);
          }
        );
      }
    });
  }
}
