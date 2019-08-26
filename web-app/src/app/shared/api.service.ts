import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpParams, HttpHeaders } from '@angular/common/http';



import { Observable, Subscriber, ReplaySubject, Subject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { WebSocketSubject } from 'rxjs/webSocket';

import { LoginModel } from '../models/login.model';
import { PictureUpdateMessage } from '../models/picture-update-messsage';
import { CameraData, CameraInfo } from '../models';


@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private loginURL = 'api/login.json';
  private cameraInfoUrl = 'api/camera/info';
  private cameraIdList = 'api/camera/{id}/idlist';
  private cameraImageIDUrl = 'api/camera/{id}/image/{stamp}';
  private webSocketURL = 'fpw2/api/socket';
  private cameraInfo$ = new ReplaySubject<CameraData>();

  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };


  constructor(private http: HttpClient) { }

  login(logindata: LoginModel): Observable<any> {
    return this.http.post(this.loginURL, logindata, { observe: 'response', responseType: 'json' });
  }

  getCameraData(): Observable<CameraData> {
    return this.cameraInfo$.asObservable();
  }

  startCameraData() {
    this.http.get(this.cameraInfoUrl).subscribe(
      (data) => {
        const cameraInfo = <CameraInfo[]>data;
        cameraInfo.forEach(x => {
          const cameraData = new CameraData(x);
          this.cameraInfo$.next(cameraData);
          this.loadHistoryList(cameraData);
        });

        this.registerSocket();
      });
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
    console.log(`--> starting socket subject on ${socketurl}`);

    new WebSocketSubject<PictureUpdateMessage>(socketurl).subscribe(
      (data) => {
        this.cameraInfo$.subscribe(cameraInfo => {
          cameraInfo.tryUpdate(data);
        }).unsubscribe();
      });
  }

  loadHistoryList(cameraData: CameraData) {
    const getURL = this.cameraIdList.replace('{id}', cameraData.getId());
    this.http.get(getURL, { observe: 'response', responseType: 'json' }).subscribe(
      (data: any) => {
        console.log(data.body);
        for (const timestamp of data.body) {
          cameraData.addURLHistory(timestamp);
        }
      }, (error: any) => {
        console.log(error);
      });
  }

}