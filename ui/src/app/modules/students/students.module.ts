import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StudentsRoutingModule } from './students-routing.module';
import { HomeComponent } from './home/home.component';
import { MaterialModule } from 'src/app/shared/material.module';
import { WorkingTestComponent } from './working-test/working-test.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { RootPageComponent } from './root-page/root-page.component';
import { TestsListComponent } from './tests-list/tests-list.component';


@NgModule({
  declarations: [
    HomeComponent,
    WorkingTestComponent,
    RootPageComponent,
    TestsListComponent
  ],
  imports: [
    CommonModule,
    StudentsRoutingModule,
    MaterialModule,
    SharedModule
  ]
})
export class StudentsModule { }
