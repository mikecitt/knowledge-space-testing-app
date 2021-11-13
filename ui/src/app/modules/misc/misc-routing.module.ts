import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RedirectGuard } from 'src/app/core/guard';
import { BaseComponent } from './base/base.component';
import { NotFoundComponent } from './not-found/not-found.component';

const routes: Routes = [
  
  {
    path: '',
    component: BaseComponent,
    canActivate: [RedirectGuard]
  },
  {
    path: '404',
    component: NotFoundComponent
  },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: '/404'
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MiscRoutingModule { }
