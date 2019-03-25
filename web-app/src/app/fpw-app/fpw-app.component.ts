import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-fpw-app',
  template: `
    <p>
      <app-sidenav></app-sidenav>
    </p>
  `,
  styles: []
})
export class FpwAppComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
