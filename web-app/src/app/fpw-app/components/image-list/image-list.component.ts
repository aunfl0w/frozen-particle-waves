import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from 'src/app/shared/api.service';
import { CameraData } from 'src/app/models';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-image-list',
  templateUrl: './image-list.component.html',
  styleUrls: ['./image-list.component.scss']
})
export class ImageListComponent implements OnInit {
  constructor(private activeRoute: ActivatedRoute, private apiService: ApiService) {
    console.log('ImageListComponent constructor');
  }
  urls: string[] = [];
  cameraId: string;
  cameraData: CameraData;
  listDescription: string;
  description = '';


  ngOnInit() {
    this.activeRoute.params.subscribe(params => {
      this.cameraId = params.id;
      const filteredObservable = this.apiService.getCameraData().pipe(filter((data => data.getId() === this.cameraId)))
      filteredObservable.subscribe(cameraData => {
        this.cameraData = cameraData;
        this.urls = cameraData.getURLHistory();
        this.listDescription = cameraData.getDescription();
      });
    });

  }
  getDescription(url: string){
    const start = url.lastIndexOf('/') + 1;
    const timestamp = url.substring(start,url.length);
    const date = new Date(Number(timestamp));
    const caldate = date.toLocaleDateString();
    const clockdate = date.toLocaleTimeString();

    return `${caldate} ${clockdate}`;
  }
}
