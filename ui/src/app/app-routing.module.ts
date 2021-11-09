import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'students',
    loadChildren: () => 
      import('./modules/students/students.module').then(m => m.StudentsModule)
  },
  {
    path: 'professors',
    loadChildren: () => 
      import('./modules/professors/professors.module').then(m => m.ProfessorsModule)
  },
  {
    path: '**',
    redirectTo: '',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
