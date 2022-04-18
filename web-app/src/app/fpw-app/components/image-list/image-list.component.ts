import { Component, OnInit, Input, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService } from 'src/app/shared/api.service';
import { CameraData } from 'src/app/models';
import { filter } from 'rxjs/operators';
import { MatSlideToggleChange } from '@angular/material/slide-toggle';
import { AppUtils } from 'src/app/shared/app.utils';

const AUTOUPDATES: string = 'autoupdates';

@Component({
  selector: 'app-image-list',
  templateUrl: './image-list.component.html',
  styleUrls: ['./image-list.component.scss']
})
export class ImageListComponent implements OnInit {
  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private apiService: ApiService
  ) {
    let updateDefault: string = localStorage.getItem(AUTOUPDATES);
    if (updateDefault && updateDefault == 'on') {
      this.autoUpdate = true;
    } else {
      this.autoUpdate = false;
    }

  }

  autoUpdate: boolean;
  urlsReference: string[];
  urls: string[] = [];
  cameraId: string;
  cameraData: CameraData;
  listDescription: string;
  description = '';
  columns: number = 1;

  ngOnInit() {
    this.activatedRoute.params.subscribe(params => {
      this.cameraId = params.id;
      const filteredObservable = this.apiService.getCameraData().pipe(filter((data => data.getId() === this.cameraId)))
      filteredObservable.subscribe(cameraData => {
        this.cameraData = cameraData;
        this.urlsReference = cameraData.getURLHistory();
        this.listDescription = cameraData.getDescription();
        this.setImageMode();

      }, error => {
        console.log('error' + error);
      }, () => {
        console.log('complete');
      });
    });
    window.scrollTo(0,0);
    this.onResize();

  }

  getDescription(url: string) {
    const start = url.lastIndexOf('/') + 1;
    const timestamp = url.substring(start, url.length);
    const date = new Date(Number(timestamp));
    const caldate = date.toLocaleDateString();
    const clockdate = date.toLocaleTimeString();
    return `${caldate} ${clockdate}`;
  }

  onResize() {
    [0, 800, 1200, 1600, 100_000].some((pixels, columnCount) => {
      if (window.innerWidth < pixels) {
        this.columns = columnCount;
        return true;
      }
    });
    AppUtils.scrollToTop(this.router);
  }

  setImageMode() {
    if (this.autoUpdate) {
      this.dynamicImageUpdateMode();
    } else {
      this.noImageUpdateMode();
    }
  }

  dynamicImageUpdateMode() {
    this.urls = this.urlsReference;

  }

  noImageUpdateMode() {
    this.urls = this.urlsReference.slice(0);
  }

  storeImageUpdateMode() {
    if (this.autoUpdate) {
      localStorage.setItem(AUTOUPDATES, 'on');
    } else {
      localStorage.setItem(AUTOUPDATES, 'off');
    }
    this.setImageMode()

  }

  updateChanged(update: MatSlideToggleChange) {
    this.autoUpdate = update.checked;
    this.storeImageUpdateMode();
  }

  imageClickEvent(event: any) {
    this.router.navigate([event], { relativeTo: this.activatedRoute.parent });

  }
}
