import { KeycloakService } from 'keycloak-angular';
import { environment } from '../../../environments/environment';

export function SSOInitializer(keycloak: KeycloakService): () => Promise<any> {
  return (): Promise<any> => {
    return new Promise(async (resolve, reject) => {
      try {
        await keycloak.init({
          config: {
            url: environment.keycloak.issuer,
            realm: environment.keycloak.realm,
            clientId: environment.keycloak.clientId
          },
          loadUserProfileAtStartUp: true,
          initOptions: {
            onLoad: 'login-required',
            checkLoginIframe: false,
            checkLoginIframeInterval: environment.checkLoginInterval
          },
          enableBearerInterceptor: true,
          bearerExcludedUrls: ['/assets']
        });
        resolve('Done !!');
      } catch (error) {
        reject(error);
      }
    });
  };
}
