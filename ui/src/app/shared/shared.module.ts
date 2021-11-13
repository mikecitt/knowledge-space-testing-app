import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainBarComponent } from './main-bar/main-bar.component';
import { MaterialModule } from './material.module';


@NgModule({
  declarations: [
    MainBarComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [
    MainBarComponent
  ]
})
export class SharedModule { }
