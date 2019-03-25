import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/shared/api.service';

@Component({
  templateUrl: './main-content.component.html',
  styleUrls: ['./main-content.component.scss']
})
export class MainContentComponent implements OnInit {
  camerainfo: any
  constructor(private apiService: ApiService) { }

  ngOnInit() {
    this.apiService.camerainfo().subscribe(
      (data: any) => {
        console.log(data);
        this.camerainfo = data;
      }, (err: any) => {
        console.error("error getting camera list: " + err);
      }
    )
  }

}
