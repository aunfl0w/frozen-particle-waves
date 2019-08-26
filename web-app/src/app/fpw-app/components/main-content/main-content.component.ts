import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/shared/api.service';
import { PictureUpdateMessage,CameraData } from 'src/app/models';

@Component({
  templateUrl: './main-content.component.html',
  styleUrls: ['./main-content.component.scss']
})
export class MainContentComponent implements OnInit {
  cameraData: CameraData[] = [];
  constructor(private apiService: ApiService) { }

  ngOnInit() {
    this.apiService.getCameraData().subscribe(data =>{
        this.cameraData.push(data);
    });
  }

}
