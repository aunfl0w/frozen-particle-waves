import { Component, OnInit, NgZone, ViewChild, ChangeDetectionStrategy } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { ApiService } from 'src/app/shared/api.service';
import { Router, ActivatedRoute } from '@angular/router';
import { CameraData } from 'src/app/models';
import { SettingsService } from '../../shared/settings.service';


const SMALL_WIDTH_BREAKPOINT = 1020;

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss']
})
export class SidenavComponent implements OnInit {
  private mediaMatcher: MediaQueryList =
    matchMedia(`(max-width: ${SMALL_WIDTH_BREAKPOINT}px)`);

  constructor(
              private zone: NgZone,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private apiService: ApiService,
              private settings: SettingsService
    ) { }
    
  cameraData: CameraData[] = [];

  @ViewChild(MatSidenav, { static: true }) sidenav: MatSidenav;

  ngOnInit() {
    this.loadCameraData();
    this.sidenav.openedStart.subscribe(() => { this.settings.setSideNavOpen(); console.log('openstart');});
    this.sidenav.closedStart.subscribe(() => { this.settings.setSideNavClosed(); console.log('closestart');});
  }
  async loadCameraData(){
    await this.apiService.getCameraData().subscribe(
      (data) => {
        this.cameraData = this.cameraData.concat([data]);
      });
  }

  clickAllCameras(): void {
    this.router.navigate(['all'], { relativeTo: this.activatedRoute });
    this.closeIfSmallScreen();
  }

  clickAllThings(): void {
    this.router.navigate(['things'], { relativeTo: this.activatedRoute });
    this.closeIfSmallScreen();
  }

  clickCamera(camera: CameraData): void {
    console.log(`clickCamera(${camera.getId()})`);
    this.router.navigate(['image-list', camera.getId()], { relativeTo: this.activatedRoute });
    this.closeIfSmallScreen();
  }

  isScreenSmall(): boolean {
    return this.settings.isSideNavClosed() || this.mediaMatcher.matches;
  }

  closeIfSmallScreen() {
    if (this.isScreenSmall()){
      this.sidenav.close();
    }
  }

  toggleSideNav() {
    this.sidenav.toggle();
  }

}
