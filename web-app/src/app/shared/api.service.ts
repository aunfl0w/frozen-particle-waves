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
  private cameraIdList = 'api/camera/{id}/idlist';
  private cameraImageIDUrl = 'api/camera/{id}/image/{stamp}'

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

  //convert this to pipe
  cameraImageList(cameraId: string): Observable<any> {
    return new Observable(subscriber => {
      let getURL = this.cameraIdList.replace('{id}', cameraId)
      this.http.get(getURL, { observe: 'response', responseType: 'json' }).subscribe(
        (data: any) => {
          console.log(data.body)
          for (let element of data.body) {
            let nextImageUrl = this.cameraImageIDUrl.replace('{id}', cameraId).replace('{stamp}', element)
            subscriber.next(nextImageUrl)
          }
        }, (error: any) => {
          console.log(error)
          subscriber.error(error)
        }
      )
    }
    )
  }





}
