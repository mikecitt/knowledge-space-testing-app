import { Injectable } from '@angular/core';
import {
    HttpRequest,
    HttpHandler,
    HttpEvent,
    HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../service/auth.service';
import { JENA_BASE } from '../constants/url.constants';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

    constructor(public auth: AuthService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (!request.url.startsWith(JENA_BASE)) {
            if (this.auth.tokenIsPresent()) {
                request = request.clone({
                    setHeaders: {
                        Authorization: `Bearer ${this.auth.getToken()}`
                    }
                });
            }
        }

        return next.handle(request);
    }
}
