import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { map } from 'rxjs/operators';
import { API_BASE } from '../constants/url.constants';
import { UserLogin, UserToken } from '../models';
import { JwtHelperService } from '@auth0/angular-jwt';

const COOKIE_NAME = 'token';

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    private access_token: string | null = null;

    private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    private readonly path = '/auth';

    constructor(
        private http: HttpClient,
        private cookieService: CookieService,
        private router: Router,
    ) {
        this.access_token = cookieService.get(COOKIE_NAME);
    }

    login(user: UserLogin) {

        return this.http.post<UserToken>(`${API_BASE}/auth/login`, user)
            .pipe(map((res) => {
                console.log('Login success:' + res.accessToken);
                this.access_token = res.accessToken;

                const expireDate = new Date();
                expireDate.setMinutes(expireDate.getMinutes() + (res.expiresIn / 60000));
                this.cookieService.set(COOKIE_NAME, this.access_token, expireDate);
                console.log(expireDate);
            }));
    }

    logout() {
        this.access_token = null;
        this.cookieService.delete(COOKIE_NAME);
        this.router.navigate(['/auth/login']);
    }

    tokenIsPresent(): boolean {
        return this.access_token != undefined && this.access_token != null && this.access_token != '';
    }

    getToken(): string | null {
        return this.access_token;
    }

    getDecodedToken() {
        const helper = new JwtHelperService();
        const token = this.cookieService.get(COOKIE_NAME);
        return token != null ? helper.decodeToken(token) : token;
    }

    getRouter(): Router {
        return this.router;
    }
}
