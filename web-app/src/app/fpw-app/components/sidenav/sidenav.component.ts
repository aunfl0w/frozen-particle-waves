import { Component, OnInit, NgZone, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material';
import { ApiService } from 'src/app/shared/api.service';


const SMALL_WIDTH_BREAKPOINT = 720;

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss']
})
export class SidenavComponent implements OnInit {
  private mediaMatcher: MediaQueryList =
    matchMedia(`(max-width: ${SMALL_WIDTH_BREAKPOINT}px)`);

  constructor(private zone: NgZone, private apiService: ApiService) {
  }
  camerainfo : any;

  @ViewChild(MatSidenav) sidenav: MatSidenav;

  ngOnInit() {
    this.apiService.camerainfo().subscribe(
      (data: any) => {
        console.log(data);
        this.camerainfo = data;
      },(err: any) => {
        console.error("error getting camera list: " + err);
      }
    )
  }

  isScreenSmall(): boolean {
    return this.mediaMatcher.matches
  }

  toggleSideNav() {
    this.sidenav.toggle();
  }

}
