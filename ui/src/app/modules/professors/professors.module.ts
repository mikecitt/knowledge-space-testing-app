import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProfessorsRoutingModule } from './professors-routing.module';
import { TestsTableComponent } from './tests-table/tests-table.component';
import { MaterialModule } from 'src/app/shared/material.module';
import { HomeComponent } from './home/home.component';
import { TestComponent } from './test/test.component';
import { TestFormComponent } from './test-form/test-form.component';


@NgModule({
  declarations: [
    HomeComponent,
    TestsTableComponent,
    TestComponent,
    TestFormComponent
  ],
  imports: [
    CommonModule,
    ProfessorsRoutingModule,
    MaterialModule
  ]
})
export class ProfessorsModule { }
