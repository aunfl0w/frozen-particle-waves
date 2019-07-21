import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpParams, HttpHeaders } from '@angular/common/http';


import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

import { LoginModel } from '../models/login.model';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private loginURL = 'api/login.json';
  private cameraInfoUrl = 'api/camera/info';
  private cameraIdList = 'api/camera/{id}/idlist';
  private cameraImageIDUrl = 'api/camera/{id}/image/{stamp}';
  private webSocketURL = 'fpw2/api/communications';
  private cameraInfo: any;

  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };


  constructor(private http: HttpClient) { }

  login(logindata: LoginModel): Observable<any> {
    return this.http.post(this.loginURL, logindata, { observe: 'response', responseType: 'json' });
  }

  camerainfo(): Observable<any> {
    return this.http.get(this.cameraInfoUrl).pipe(
      tap(val =>
        this.cameraInfo = val
      )
    );
  }

  cameraImageList(cameraId: string): Observable<any> {
    return new Observable(subscriber => {
      const getURL = this.cameraIdList.replace('{id}', cameraId);
      this.http.get(getURL, { observe: 'response', responseType: 'json' }).subscribe(
        (data: any) => {
          console.log(data.body);
          for (const element of data.body) {
            const nextImageUrl = this.cameraImageIDUrl.replace('{id}', cameraId).replace('{stamp}', element);
            subscriber.next(nextImageUrl);
          }
        }, (error: any) => {
          console.log(error);
          subscriber.error(error);
        }
      );
    }
    );
  }

  getCameraName(cameraId: string): string {
    for (const camera of this.cameraInfo) {
      console.log(camera);
      if (camera.id === cameraId) {
        return camera.description;
      }
    }
    return 'Unknown';
  }

  registerSocket() {
    const loc = window.location;
    let socketurl;
    if (loc.protocol === 'https:') {
      socketurl = 'wss:';
    } else {
      socketurl = 'ws:';
    }
    socketurl += '//' + loc.host + '/';
    socketurl += this.webSocketURL;
    console.log(socketurl);
    const socket = new WebSocket(socketurl);

    socket.onmessage = (message) => {
      console.log(message);
    };
  }

}

