import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../service/auth.service';

@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanActivate {

  constructor(private router: Router, private authService: AuthService) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

      if(!this.authService.tokenIsPresent()) {
        return true;
      } else {
        const decodedToken = this.authService.getDecodedToken();
        if(decodedToken.role == 'ROLE_STUDENT') {
          this.router.navigate(['/students']);
        } else if(decodedToken.role == 'ROLE_PROFESSOR') {
          this.router.navigate(['/professors']);
        } else {
          this.router.navigate(['/']);
        }
        return false;
      }
  }

}
