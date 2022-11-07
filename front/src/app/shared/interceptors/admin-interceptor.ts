import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/pages/auth/service/auth.service';

@Injectable()
export class AdminInterceptor implements HttpInterceptor {
  constructor(private authSvc: AuthService, private _router: Router) {}
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    if (
      req.url.includes('courses') ||
      req.url.includes('subscriptions') ||
      req.url.includes('users')
    ) {
      const userValue = this.authSvc.userValue;

      if (!userValue) {
        this._router.navigate(['']);
        return next.handle(req);
      } else {
        const authRequest = req.clone({
          setHeaders: {
            Authorization: `Bearer ${userValue.accessToken}`,
          },
        });

        return next.handle(authRequest);
      }
    }

    return next.handle(req);
  }
}
