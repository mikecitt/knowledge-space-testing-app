import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginGuard, AuthGuard } from './core/guard';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'students',
        loadChildren: () =>
          import('./modules/students/students.module').then(m => m.StudentsModule),
      },
      {
        path: 'professors',
        loadChildren: () =>
          import('./modules/professors/professors.module').then(m => m.ProfessorsModule),
      }
    ],
    canActivate: [AuthGuard]
  },
  {
    path: 'auth',
    loadChildren: () =>
      import('./modules/auth/auth.module').then(m => m.AuthModule),
    canActivate: [LoginGuard]
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
