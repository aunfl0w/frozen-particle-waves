import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable, Subscriber, ReplaySubject, Subject } from 'rxjs';
import { WebSocketSubject } from 'rxjs/webSocket';

import { LoginModel } from '../models/login.model';
import { PictureUpdateMessage } from '../models/picture-update-messsage';
import { CameraData, CameraInfo } from '../models';
import { ThingsCommandModel } from '../models/things.command.model';
import { timeout } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private loginURL = 'api/login.json';
  private cameraInfoUrl = 'api/camera/info';
  private cameraIdList = 'api/camera/{id}/idlist';
  private cameraImageIDUrl = 'api/camera/{id}/image/{stamp}';
  private thingsCommandURL = 'api/light/{id}/command';
  private webSocketURL = 'fpw2/api/socket';
  private statusURL = 'api/status';
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

  thingsCommand(id: string, command: ThingsCommandModel): Observable<any> {
    const commandURL = this.thingsCommandURL.replace("{id}", id);
    return this.http.post(commandURL, command, { observe: 'response', responseType: 'json' });
  }

  status(): Observable<any> {
    return this.http.get(this.statusURL, { withCredentials: true });
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
    let socketurl: string;
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
      }, (error) => {
        console.error(error);
        setTimeout(window.location.reload, 1000 * 600 /* 10 minutes */);
      });
  }

  loadHistoryList(cameraData: CameraData) {
    const getURL = this.cameraIdList.replace('{id}', cameraData.getId());
    this.http.get(getURL, { observe: 'response', responseType: 'json' }).subscribe(
      (data: any) => {
        for (const timestamp of data.body) {
          cameraData.addURLHistory(timestamp);
        }
      }, (error: any) => {
        console.log(error);
      });
  }

}