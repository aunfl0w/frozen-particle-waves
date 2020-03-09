import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { CameraData } from 'src/app/models';


@Component({
  selector: 'app-camera',
  templateUrl: './camera.component.html',
  styleUrls: ['./camera.component.scss']
})
export class CameraComponent implements OnInit {
  @Input() cameraData: CameraData;
  @Input() update?: boolean = true;
  @Input() imageUrl?: string;
  @Input() clickData?: string;
  @Input() description?: string;
  @Input() lazyLoad?: boolean = true;
  @Output() clickImage: EventEmitter<any> = new EventEmitter();

  constructor() { }

  ngOnInit() {
    if (this.update) {
      this.cameraData.getURL().subscribe(url => {
        this.imageUrl = url;
      });
    }
  }
  getLazy(): string {
    return this.lazyLoad ? 'lazy' : null;
  }

  imageClickEvent(event: any, actionUrl: string) {
    this.clickImage.emit(actionUrl);
  }
}
