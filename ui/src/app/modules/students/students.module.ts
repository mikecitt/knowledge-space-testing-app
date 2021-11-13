import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StudentsRoutingModule } from './students-routing.module';
import { HomeComponent } from './home/home.component';
import { MaterialModule } from 'src/app/shared/material.module';
import { WorkingTestComponent } from './working-test/working-test.component';
import { MainBarComponent } from 'src/app/shared/main-bar/main-bar.component';


@NgModule({
  declarations: [
    HomeComponent,
    WorkingTestComponent,
    MainBarComponent
  ],
  imports: [
    CommonModule,
    StudentsRoutingModule,
    MaterialModule
  ]
})
export class StudentsModule { }
