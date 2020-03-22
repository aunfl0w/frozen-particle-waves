import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { ApiService } from 'src/app/shared/api.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CameraData } from 'src/app/models';
import { AppUtils } from 'src/app/shared/app.utils';




@Component({
  templateUrl: './main-content.component.html',
  styleUrls: ['./main-content.component.scss']
})
export class MainContentComponent implements OnInit {

  cameraData: CameraData[] = [];
  columns: number = 1;
  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private apiService: ApiService,
  ) { }

  ngOnInit() {
    this.apiService.getCameraData().subscribe(data => {
      this.cameraData.push(data);
    });
    this.onResize();
    AppUtils.scrollToTop(this.router);
  }

  onResize() {
    [0, 800, 1500, 2000, 100_000].some((pixels, columnCount) => {
      if (window.innerWidth < pixels) {
        this.columns = columnCount;
        return true;
      }
    });
  }

  imageClickEvent(event: any) {
    this.router.navigate(['image-list', event], { relativeTo: this.activatedRoute.parent });
  }
}


