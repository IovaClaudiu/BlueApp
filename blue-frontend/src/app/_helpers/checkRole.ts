import { AuthenticationService } from './../_services/authentication.service';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class CheckUserRole {
  constructor(private authService: AuthenticationService) {}

  public verifyHasRights(): boolean {
    return this.authService.currentUserValue.role === 'ROLE_ADMIN';
  }
}
