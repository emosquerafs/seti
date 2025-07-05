import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DocumentTypesListComponent } from './document-types-list/document-types-list.component';

const routes: Routes = [
  { path: '', component: DocumentTypesListComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DocumentTypesRoutingModule { }
