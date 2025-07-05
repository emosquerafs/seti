import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PersonsListComponent } from './persons-list/persons-list.component';

const routes: Routes = [
  { path: '', component: PersonsListComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PersonsRoutingModule { }
