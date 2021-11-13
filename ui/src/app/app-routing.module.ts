import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginGuard, AuthGuard, ProfessorGuard, StudentGuard } from './core/guard';

const route: string = "students";

const routes: Routes = [
  {
    path: 'students',
    loadChildren: () =>
      import('./modules/students/students.module').then(m => m.StudentsModule),
    canActivate: [AuthGuard, StudentGuard]
  },
  {
    path: 'professors',
    loadChildren: () =>
      import('./modules/professors/professors.module').then(m => m.ProfessorsModule),
    canActivate: [AuthGuard, ProfessorGuard]
  },
  {
    path: 'auth',
    loadChildren: () =>
      import('./modules/auth/auth.module').then(m => m.AuthModule),
    canActivate: [LoginGuard]
  },
  {
    path: '',
    loadChildren: () =>
      import('./modules/misc/misc.module').then(m => m.MiscModule),
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
