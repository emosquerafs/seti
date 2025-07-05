import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MessageService, ConfirmationService } from 'primeng/api';

import { EmployeesRoutingModule } from './employees-routing.module';
import { EmployeesListComponent } from './employees-list/employees-list.component';
import { PrimeAllModule } from '../../common/prime-all.module';

@NgModule({
  declarations: [
    EmployeesListComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    EmployeesRoutingModule,
    PrimeAllModule
  ],
  providers: [
    MessageService,
    ConfirmationService
  ]
})
export class EmployeesModule { }
