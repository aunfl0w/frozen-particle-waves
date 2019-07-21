import { Component, OnInit, NgZone, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material';
import { ApiService } from 'src/app/shared/api.service';
import { Router, ActivatedRoute } from '@angular/router';


const SMALL_WIDTH_BREAKPOINT = 720;

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss']
})
export class SidenavComponent implements OnInit {
  private mediaMatcher: MediaQueryList =
    matchMedia(`(max-width: ${SMALL_WIDTH_BREAKPOINT}px)`);

  constructor(private zone: NgZone,
              private apiService: ApiService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
  }
  camerainfo: any;

  @ViewChild(MatSidenav) sidenav: MatSidenav;

  ngOnInit() {
    this.apiService.camerainfo().subscribe(
      (data: any) => {
        console.log(data);
        this.camerainfo = data;
      }, (err: any) => {
        console.error('error getting camera list: ' + err);
      }
    );
  }

  clickAllCameras(): void {
    this.router.navigate(['all'], { relativeTo: this.activatedRoute });
  }

  clickCamera(cameraId: string): void {
    console.log('clickCamera()');
    console.log(cameraId);
    this.router.navigate(['image-list', cameraId], { relativeTo: this.activatedRoute });

  }

  isScreenSmall(): boolean {
    return this.mediaMatcher.matches;
  }

  toggleSideNav() {
    this.sidenav.toggle();
  }

}
