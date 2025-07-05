import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { AppConfig, LayoutService } from '../services/layout.service';
import { KeycloakService } from 'keycloak-angular';
import { TranslateService } from '@ngx-translate/core';
import { PrimeNGConfig } from 'primeng/api';
import { LanguageService } from '../../../utils/services/language.service';

interface Language {
  name: string;
  code: string;
}

@Component({
  selector: 'app-topbar',
  templateUrl: './topbar.component.html',
  styleUrl: './topbar.component.scss'
})
export class TopbarComponent implements OnInit {
  @ViewChild('menuButton')
  menuButton!: ElementRef;
  @ViewChild('mobileMenuButton')
  mobileMenuButton!: ElementRef;
  config!: AppConfig;
  subscription: any;
  fullName: string | undefined;
  email: string | undefined;
  login: string | undefined;
  selectedLanguage!: Language;
  languges: Language[] = [];
  constructor(
    public layoutService: LayoutService,
    public el: ElementRef,
    private keycloakSvc: KeycloakService,
    private languageService: LanguageService
  ) {
    this.subscription = this.layoutService.configUpdate$.subscribe(config => {
      this.config = config;
    });

    this.languges = [
      {
        name: 'Español',
        code: 'es-CO'
      },
      {
        name: 'English',
        code: 'en-US'
      }
    ];
  }

  onLanguageChange(event: any): void {
    const selectedLanguage = event.value.code;
    this.languageService.changeLanguage(selectedLanguage);
    localStorage.setItem('preferredLanguage', selectedLanguage);
  }

  async ngOnInit(): Promise<void> {
    const preferredLanguageCode = localStorage.getItem('preferredLanguage');
    if (preferredLanguageCode) {
      this.selectedLanguage = this.languges.find(lang => lang.code === preferredLanguageCode) || this.languges[0];
      this.languageService.changeLanguage(preferredLanguageCode);
    } else {
      this.selectedLanguage = this.languges[0];
    }

    await this.keycloakSvc.loadUserProfile(true).then(userProfile => {
      this.fullName =
        userProfile.firstName
          ?.toLocaleUpperCase()
          .concat(' ')
          .concat(userProfile.lastName?.toLocaleUpperCase() || '') || '';
      this.email = userProfile.email;

      this.login = userProfile.username;
      return this.fullName;
    });
  }

  onMenuButtonClick() {
    this.layoutService.onMenuToggle();
  }

  async onLogOutlick(): Promise<void> {
    const a = await this.keycloakSvc.logout(window.location.origin);
  }
}
