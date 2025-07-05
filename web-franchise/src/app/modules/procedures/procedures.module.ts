import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MessageService, ConfirmationService } from 'primeng/api';

import { ProceduresRoutingModule } from './procedures-routing.module';
import { ProceduresListComponent } from './procedures-list/procedures-list.component';
import { PrimeAllModule } from '../../common/prime-all.module';

@NgModule({
  declarations: [
    ProceduresListComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ProceduresRoutingModule,
    PrimeAllModule
  ],
  providers: [
    MessageService,
    ConfirmationService
  ]
})
export class ProceduresModule { }
