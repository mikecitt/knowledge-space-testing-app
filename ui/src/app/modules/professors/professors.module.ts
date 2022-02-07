import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';

import { ProfessorsRoutingModule } from './professors-routing.module';
import { TestsTableComponent } from './tests-table/tests-table.component';
import { MaterialModule } from 'src/app/shared/material.module';
import { HomeComponent } from './home/home.component';
import { TestComponent } from './test/test.component';
import { TestFormComponent } from './test-form/test-form.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { RootPageComponent } from './root-page/root-page.component';
import { SectionFormComponent } from './section-form/section-form.component';
import { ItemComponent } from './item/item.component';
import { ItemFormComponent } from './item-form/item-form.component';
import { ConfirmDialogComponent } from './confirm-dialog/confirm-dialog.component';
import { DomainComponent } from './domain/domain.component';
import { KnowledgeSpaceComponent } from './knowledge-space/knowledge-space.component';
import { DomainFormComponent } from './domain-form/domain-form.component';
import { KnowledgeSpaceFormComponent } from './knowledge-space-form/knowledge-space-form.component';
import { QueryComponent } from './query/query.component';
import { KnowledgeSpaceComparisonComponent } from './knowledge-space-comparison/knowledge-space-comparison.component';
import { NgxGraphModule } from '@swimlane/ngx-graph';


@NgModule({
  declarations: [
    HomeComponent,
    TestsTableComponent,
    TestComponent,
    TestFormComponent,
    RootPageComponent,
    SectionFormComponent,
    ItemComponent,
    ItemFormComponent,
    ConfirmDialogComponent,
    DomainComponent,
    KnowledgeSpaceComponent,
    DomainFormComponent,
    KnowledgeSpaceFormComponent,
    QueryComponent,
    KnowledgeSpaceComparisonComponent
  ],
  imports: [
    CommonModule,
    ProfessorsRoutingModule,
    MaterialModule,
    SharedModule,
    NgxGraphModule
  ],
  providers: [DatePipe]
})
export class ProfessorsModule { }
