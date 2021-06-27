import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule } from '@angular/forms';

import { ToolbarComponent } from './components/toolbar/toolbar.component';
import { SidenavComponent } from './components/sidenav/sidenav.component';
import { MainContentComponent } from './components/main-content/main-content.component';
import { FpwAppComponent } from './fpw-app.component';
import { MaterialModule } from '../shared/material.module';
import { ImageListComponent } from './components/image-list/image-list.component';
import { CameraComponent } from './components/camera/camera.component';
import { VideoComponent } from './components/video/video.component';
import { ThingsComponent } from './components/things/things.component';
import { LightDimmableComponent } from './components/light-dimmable/light-dimmable.component';
import { LightColorComponent } from './components/light-color/light-color.component';

const routes: Routes = [
  {
    path: '', component: FpwAppComponent,
    children: [
      { path: '', redirectTo: 'all' },
      { path: 'all', component: MainContentComponent },
      { path: 'image-list/:id', component: ImageListComponent },
      { path: 'video/:id', component: VideoComponent },
      { path: 'things', component: ThingsComponent }

    ]
  }
];

@NgModule({
  declarations: [
    ToolbarComponent,
    SidenavComponent,
    MainContentComponent,
    ImageListComponent,
    CameraComponent,
    VideoComponent,
    FpwAppComponent,
    ThingsComponent,
    LightDimmableComponent,
    LightColorComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FlexLayoutModule,
    FormsModule,
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class FpwModule { }
