import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/shared/api.service';
import { CameraData } from 'src/app/models';


@Component({
  templateUrl: './main-content.component.html',
  styleUrls: ['./main-content.component.scss']
})

export class MainContentComponent implements OnInit {
  cameraData: CameraData[] = [];
  columns: number = 1;
  constructor(private apiService: ApiService) { }

  ngOnInit() {
    this.apiService.getCameraData().subscribe(data => {
      this.cameraData.push(data);
    });
    this.onResize();
  }
  onResize() {
    [0, 800, 1500, 2000, 100_000].some((pixels, columnCount) => {
      if (window.innerWidth < pixels) {
        this.columns = columnCount;
        return true;
      }
    });
  }

}


