import { Component, ElementRef, OnDestroy, ViewChild } from '@angular/core';
import { LayoutService } from '../services/layout.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent implements OnDestroy {
  timeout: any = null;

  @ViewChild('menuContainer')
  menuContainer!: ElementRef;

  constructor(
    public layoutService: LayoutService,
    public el: ElementRef
  ) {}

  resetOverlay() {
    if (this.layoutService.state.overlayMenuActive) {
      this.layoutService.state.overlayMenuActive = false;
    }
  }

  ngOnDestroy() {
    this.resetOverlay();
  }
}
