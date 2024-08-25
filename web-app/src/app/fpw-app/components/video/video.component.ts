import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-video',
  templateUrl: './video.component.html',
  styleUrls: ['./video.component.scss']
})
export class VideoComponent implements OnInit {

  constructor(private activeRoute: ActivatedRoute) { }
  cameraId: string;
  videocam: string;

  ngOnInit() {
    this.activeRoute.params.subscribe(params => {
      this.cameraId  = params.id;
      this.videocam = `/fpw2/api/camera/${this.cameraId}/video`;
    });
  }

}
