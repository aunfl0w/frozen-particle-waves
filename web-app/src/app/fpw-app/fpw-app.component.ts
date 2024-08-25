import { Component, OnInit } from '@angular/core';
import { ApiService } from '../shared/api.service';

@Component({
  selector: 'app-fpw-app',
  template: `
      <app-sidenav></app-sidenav>
  `,
  styles: []
})
export class FpwAppComponent implements OnInit {

  constructor(private apiService: ApiService) { }
  
  ngOnInit() {
    this.apiService.startCameraData();
  }

}
