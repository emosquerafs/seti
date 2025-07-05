import { Component } from '@angular/core';
import { LayoutService } from '../services/layout.service';

import { version } from '../../../../../package.json';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.scss'
})
export class FooterComponent {
  appVersion = version;
  constructor(public layoutService: LayoutService) {}
}
