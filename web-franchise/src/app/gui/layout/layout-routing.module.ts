import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutComponent } from './layout/layout.component';

const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      {
        path: 'document-types',
        loadChildren: () => import('../../modules/document-types/document-types.module').then(m => m.DocumentTypesModule)
      },
      {
        path: 'persons',
        loadChildren: () => import('../../modules/persons/persons.module').then(m => m.PersonsModule)
      },
      {
        path: 'employees',
        loadChildren: () => import('../../modules/employees/employees.module').then(m => m.EmployeesModule)
      },
      {
        path: 'procedures',
        loadChildren: () => import('../../modules/procedures/procedures.module').then(m => m.ProceduresModule)
      },
      { path: '', redirectTo: 'document-types', pathMatch: 'full' }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LayoutRoutingModule {}
