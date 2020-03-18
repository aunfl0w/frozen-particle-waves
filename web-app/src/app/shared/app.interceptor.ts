import { Injectable } from '@angular/core';
import {
    HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse
} from '@angular/common/http';

import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable()
export class AppInterceptor implements HttpInterceptor {
    constructor(private router: Router) { }

    /**
     * if response status is 401 and url does not contain status redirect to login page
     * @param req 
     * @param next 
     */
    intercept(req: HttpRequest<any>, next: HttpHandler):
        Observable<HttpEvent<any>> {
        let hasStaus = /status/;
        if (!hasStaus.test(req.url)) {
            return next.handle(req).pipe(
                tap(() => { },
                    (error: any) => {
                        console.log(error);
                        if (error instanceof HttpErrorResponse) {
                            let httpErr: HttpErrorResponse = error;
                            if (httpErr.status == 401) {
                                this.router.navigate(['login']);
                            }
                        }
                    }));
        } else {
            return next.handle(req);
        }
    }
}