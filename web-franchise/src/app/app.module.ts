import { APP_INITIALIZER, CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CommonModule, LocationStrategy, PathLocationStrategy } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { SSOInitializer } from './utils/sso/sso-app-init';
import { HttpBackend, HttpClientModule } from '@angular/common/http';

import { TranslateLoader, TranslateModule } from '@ngx-translate/core';

import { HttpClient, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';

import { HttpLoaderFactory } from '../app/utils/http-loader-factory';
import { LanguageService } from './utils/services/language.service';

@NgModule({
  declarations: [AppComponent],
  imports: [
    CommonModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    KeycloakAngularModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpBackend]
      }
    })
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],

  providers: [
    LanguageService,
    {
      provide: LocationStrategy,
      useClass: PathLocationStrategy
    },
    {
      provide: APP_INITIALIZER,
      useFactory: SSOInitializer,
      multi: true,
      deps: [KeycloakService]
    },
    provideHttpClient(withInterceptorsFromDi())
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
