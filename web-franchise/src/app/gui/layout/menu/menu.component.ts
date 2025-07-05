import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { LanguageService } from '../../../utils/services/language.service';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent implements OnInit {
  model: MenuItem[] = [];

  constructor(
    private translateService: TranslateService,
    private languageService: LanguageService
  ) {}

  ngOnInit() {
    this.languageService.currentLanguage$.subscribe(lang => {
      this.translateService
        .get([
          'menu.management.title',
          'menu.management.documentTypes',
          'menu.management.persons',
          'menu.management.employees',
          'menu.management.procedures'
        ])
        .subscribe(translations => {
          this.model = [
            {
              label: translations['menu.management.title'],
              icon: 'pi pi-th-large',
              items: [
                {
                  label: translations['menu.management.documentTypes'],
                  icon: 'pi pi-id-card',
                  routerLink: ['/document-types']
                },
                {
                  label: translations['menu.management.persons'],
                  icon: 'pi pi-users',
                  routerLink: ['/persons']
                },
                {
                  label: translations['menu.management.employees'],
                  icon: 'pi pi-user-edit',
                  routerLink: ['/employees']
                },
                {
                  label: translations['menu.management.procedures'],
                  icon: 'pi pi-file-edit',
                  routerLink: ['/procedures']
                }
              ]
            }
          ];
        });
    });
  }
}
