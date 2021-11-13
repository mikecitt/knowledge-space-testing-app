import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { API_BASE } from '../constants/url.constants';
import { UserLogin, UserToken } from '../models';
import { JwtHelperService } from '@auth0/angular-jwt';

const STORAGE_NAME = 'token';

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    private userToken: UserToken | null = null;

    private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    private readonly path = '/auth';

    private extractToken(): void {
        var stringified = localStorage.getItem(STORAGE_NAME);
        if (stringified) {
            this.userToken = JSON.parse(stringified);
        }
    }

    constructor(
        private http: HttpClient,
        private router: Router,
    ) {
        this.extractToken();
        if(this.userToken && this.userToken.expiresIn < new Date().getTime()) {
            this.logout();
        }
    }

    login(user: UserLogin) {
        return this.http.post<UserToken>(`${API_BASE}/auth/login`, user)
            .pipe(map((res) => {
                console.log('Login success:' + res.accessToken);
                var accessToken = res.accessToken;

                const expireDate = new Date();
                expireDate.setMinutes(expireDate.getMinutes() + (res.expiresIn / 60000));
                this.userToken = {
                    accessToken: accessToken,
                    expiresIn: expireDate.getTime()
                };
                localStorage.setItem(STORAGE_NAME, JSON.stringify(this.userToken));
                console.log(expireDate);
            }));
    }

    logout() {
        this.userToken = null;
        localStorage.removeItem(STORAGE_NAME);
        this.router.navigate(['/auth/login']);
    }

    tokenIsPresent(): boolean {
        return this.userToken != undefined && this.userToken != null;
    }

    getToken(): string | null {
        return this.userToken ? this.userToken.accessToken : null;
    }

    getDecodedToken() {
        const helper = new JwtHelperService();
        const token = this.userToken;
        return token != null ? helper.decodeToken(token.accessToken) : token;
    }

    getRouter(): Router {
        return this.router;
    }
}
