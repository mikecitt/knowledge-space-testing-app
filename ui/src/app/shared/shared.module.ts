import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainBarComponent } from './main-bar/main-bar.component';
import { MaterialModule } from './material.module';
import { ConfirmationDialogComponent } from './confirmation-dialog/confirmation-dialog.component';


@NgModule({
  declarations: [
    MainBarComponent,
    ConfirmationDialogComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [
    MainBarComponent,
    ConfirmationDialogComponent
  ]
})
export class SharedModule { }
