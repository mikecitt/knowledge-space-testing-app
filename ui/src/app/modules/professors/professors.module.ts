import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

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
    ConfirmDialogComponent
  ],
  imports: [
    CommonModule,
    ProfessorsRoutingModule,
    MaterialModule,
    SharedModule
  ]
})
export class ProfessorsModule { }
