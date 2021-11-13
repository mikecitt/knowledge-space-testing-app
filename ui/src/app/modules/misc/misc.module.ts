import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MiscRoutingModule } from './misc-routing.module';
import { NotFoundComponent } from './not-found/not-found.component';
import { MaterialModule } from 'src/app/shared/material.module';
import { BaseComponent } from './base/base.component';


@NgModule({
  declarations: [
    NotFoundComponent,
    BaseComponent
  ],
  imports: [
    CommonModule,
    MiscRoutingModule,
    MaterialModule
  ]
})
export class MiscModule { }
