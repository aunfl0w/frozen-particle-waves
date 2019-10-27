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

const routes: Routes = [
  {
    path: '', component: FpwAppComponent,
    children: [
      { path: '', redirectTo: 'all' },
      { path: 'all', component: MainContentComponent },
      { path: 'image-list/:id', component: ImageListComponent },
      { path: 'video/:id', component: VideoComponent }
    ]
  }
];

@NgModule({
  declarations: [
    ToolbarComponent,
    SidenavComponent,
    MainContentComponent,
    FpwAppComponent,
    ImageListComponent,
    CameraComponent,
    VideoComponent
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
