import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-camera',
  templateUrl: './camera.component.html',
  styleUrls: ['./camera.component.scss']
})
export class CameraComponent implements OnInit {
  @Input() description: string
  @Input() cameraId: string
  constructor() { }

  ngOnInit() {
  }

  getCameraURL() : string {
    return 'api/camera/'+this.cameraId+'/image'
  }

}
