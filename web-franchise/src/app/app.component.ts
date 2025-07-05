import { Component, LOCALE_ID, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { KeycloakEventType, KeycloakService } from 'keycloak-angular';
import { PrimeNGConfig } from 'primeng/api';
import { LanguageService } from './utils/services/language.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  providers: [TranslateService, { provide: LOCALE_ID, useValue: 'es-CO' }]
})
export class AppComponent implements OnInit {
  constructor(
    private primengConfig: PrimeNGConfig,
    private keycloakService: KeycloakService,
    private translateService: TranslateService,
    private languageService: LanguageService
  ) {
    const preferredLanguage = localStorage.getItem('preferredLanguage');
    if (preferredLanguage) {
      this.languageService.changeLanguage(preferredLanguage);
    } else {
      const browserLang = translateService.getBrowserLang();
      let lang = browserLang?.match(/en|es/) ? browserLang : 'es';
      this.languageService.changeLanguage(lang === 'es' ? lang.concat('-CO') : lang.concat('-US'));
    }
  }

  ngOnInit(): void {
    this.primengConfig.ripple = true;
    this.keycloakService.keycloakEvents$.subscribe({
      next: async e => {
        if (e.type == KeycloakEventType.OnTokenExpired) {
          const a = await this.keycloakService.logout(window.location.origin);
        }
      }
    });
    this.languageService.currentLanguage$.subscribe(lang => {
      this.translateService.use(lang);
      this.translateService.stream('primeng').subscribe(data => this.primengConfig.setTranslation(data));
    });
  }
}
