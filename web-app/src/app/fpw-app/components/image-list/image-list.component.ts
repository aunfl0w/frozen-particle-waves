import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from 'src/app/shared/api.service';

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
  cameraName = '';


  @Input() cameraId: string;


  dataupdater = (data: any) => {
    console.log(data);
    this.urls.push(data);
  }



  ngOnInit() {
    this.cameraId = this.activeRoute.snapshot.params.id;
    this.activeRoute.params.subscribe(params => {
      this.urls = [];
      this.cameraId = params.id;
      this.apiService.cameraImageList(this.cameraId).subscribe(this.dataupdater);
      this.cameraName = this.apiService.getCameraName(this.cameraId);
    });

  }

}
