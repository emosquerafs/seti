import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MessageService, ConfirmationService } from 'primeng/api';

import { PersonsRoutingModule } from './persons-routing.module';
import { PersonsListComponent } from './persons-list/persons-list.component';
import { PrimeAllModule } from '../../common/prime-all.module';

@NgModule({
  declarations: [
    PersonsListComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    PersonsRoutingModule,
    PrimeAllModule
  ],
  providers: [
    MessageService,
    ConfirmationService
  ]
})
export class PersonsModule { }
