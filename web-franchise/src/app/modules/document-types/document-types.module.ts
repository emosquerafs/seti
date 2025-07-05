import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MessageService, ConfirmationService } from 'primeng/api';

import { DocumentTypesRoutingModule } from './document-types-routing.module';
import { DocumentTypesListComponent } from './document-types-list/document-types-list.component';
import { PrimeAllModule } from '../../common/prime-all.module';

@NgModule({
  declarations: [
    DocumentTypesListComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    DocumentTypesRoutingModule,
    PrimeAllModule
  ],
  providers: [
    MessageService,
    ConfirmationService
  ]
})
export class DocumentTypesModule { }
