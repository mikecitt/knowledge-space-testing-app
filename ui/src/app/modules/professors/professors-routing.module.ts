import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DomainComponent } from './domain/domain.component';
import { HomeComponent } from './home/home.component';
import { KnowledgeSpaceComparisonComponent } from './knowledge-space-comparison/knowledge-space-comparison.component';
import { KnowledgeSpaceComponent } from './knowledge-space/knowledge-space.component';
import { QueryComponent } from './query/query.component';
import { RootPageComponent } from './root-page/root-page.component';
import { TestComponent } from './test/test.component';

const routes: Routes = [
  {
    path: '',
    component: RootPageComponent,
    children: [
      {
        path: '',
        component: HomeComponent
      },
      {
        path: 'domain',
        component: DomainComponent
      },
      {
        path: 'knowledge-space',
        component: KnowledgeSpaceComponent
      },
      {
        path: 'knowledge-space-comparison/:id',
        component: KnowledgeSpaceComparisonComponent
      },
      {
        path: 'test/:id',
        component: TestComponent
      },
      {
        path: 'queries',
        component: QueryComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfessorsRoutingModule { }
