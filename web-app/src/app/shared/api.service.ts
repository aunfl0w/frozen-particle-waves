import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpParams, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { LoginModel } from '../models/login.model';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private loginURL = 'api/login.json';
  private cameraInfoUrl = 'api/camera/info';

  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };


  constructor(private http: HttpClient) { }

  login(logindata: LoginModel): Observable<any> {
    return this.http.post(this.loginURL, logindata, { observe: 'response', responseType: 'json' });
  }

  camerainfo(): Observable<any> {
    return this.http.get(this.cameraInfoUrl);
  }



}
