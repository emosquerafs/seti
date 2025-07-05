import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { UppercaseDirective } from '../utils/directives/uppercase.directive';

const POSEIDON_MODULE = [FormsModule, RouterLink, HttpClientModule, ReactiveFormsModule];

@NgModule({
  declarations: [UppercaseDirective],
  imports: [CommonModule, POSEIDON_MODULE],
  exports: [POSEIDON_MODULE, UppercaseDirective]
})
export class PoseidonCommonModule {}
