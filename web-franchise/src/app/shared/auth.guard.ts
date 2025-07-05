import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { KeycloakAuthGuard, KeycloakService } from 'keycloak-angular';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard extends KeycloakAuthGuard {
  async isAccessAllowed(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean | UrlTree> {
    let authorized = false;

    if (!this.authenticated || !this.keycloakAngular.isTokenExpired) {
      await this.keycloak.login({
        redirectUri: window.location.origin
      });
    }

    const requiredRoles = route.data['allowedRoles'];

    if (!(requiredRoles instanceof Array) || requiredRoles.length === 0) {
      return true;
    }

    requiredRoles.forEach(role => {
      if (this.roles.includes(role)) {
        authorized = true;
      }
    });

    if (!authorized) {
      this.router.navigate(['/accessdenied']);
    }

    return authorized;
  }

  constructor(
    protected override readonly router: Router,
    protected readonly keycloak: KeycloakService
  ) {
    super(router, keycloak);
  }
}
