import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { BehaviorSubject } from 'rxjs';
import { PrimeNGConfig } from 'primeng/api';

@Injectable({
  providedIn: 'root'
})
export class LanguageService {
  private currentLanguage = new BehaviorSubject<string>('es-CO');
  currentLanguage$ = this.currentLanguage.asObservable();

  constructor(
    private translateService: TranslateService,
    private primeNGConfig: PrimeNGConfig
  ) {}

  changeLanguage(lang: string) {
    this.translateService.use(lang);
    this.translateService.stream('primeng').subscribe(data => this.primeNGConfig.setTranslation(data));
    this.currentLanguage.next(lang);
  }
}
