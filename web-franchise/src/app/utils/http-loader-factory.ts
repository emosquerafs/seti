import { MultiTranslateHttpLoader } from 'ngx-translate-multi-http-loader';

import { HttpBackend } from '@angular/common/http';

export function HttpLoaderFactory(http: HttpBackend) {
  return new MultiTranslateHttpLoader(http, [
    { prefix: '../../assets/i18n/bussiness/organization/purchase-', suffix: '.json' },
    { prefix: '../../assets/i18n/bussiness/organization/supplier-', suffix: '.json' },
    { prefix: '../../assets/i18n/menu-', suffix: '.json' },
    { prefix: '../../assets/i18n/bussiness/organization/primeng-', suffix: '.json' },
    { prefix: '../../assets/i18n/bussiness/organization/products-', suffix: '.json' },
    { prefix: '../../assets/i18n/bussiness/organization/patients-', suffix: '.json' },
    { prefix: '../../assets/i18n/bussiness/organization/eps-', suffix: '.json' },
    { prefix: '../../assets/i18n/common-', suffix: '.json' },
    { prefix: '../../assets/i18n/bussiness/static-data/country-', suffix: '.json' },
    { prefix: '../../assets/i18n/bussiness/static-data/city-', suffix: '.json' },
    { prefix: '../../assets/i18n/bussiness/static-data/level-', suffix: '.json' },
    { prefix: '../../assets/i18n/bussiness/static-data/state-', suffix: '.json' },
    { prefix: '../../assets/i18n/bussiness/static-data/document-type-', suffix: '.json' },
    { prefix: '../../assets/i18n/bussiness/organization/billing-submission-', suffix: '.json' }
  ]);
}
