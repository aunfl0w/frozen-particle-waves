import { Injectable } from '@angular/core';

const SIDE_NAV_STATE = 'sidenavstateclosed';
@Injectable({
  providedIn: 'root'
})
export class SettingsService {
  constructor() { }
  public isSideNavClosed() : boolean {
    let sideNaveState : any = localStorage.getItem(SIDE_NAV_STATE);
    return sideNaveState === 'true';
  }

  public setSideNavClosed() : void {
    let sideNaveState : any = localStorage.setItem(SIDE_NAV_STATE, "true");
  }

  public setSideNavOpen() : void {
    let sideNaveState : any = localStorage.setItem(SIDE_NAV_STATE, "false");
  }

}
