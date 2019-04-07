(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["fpw-app-fpw-module"],{

/***/ "./src/app/fpw-app/components/camera/camera.component.html":
/*!*****************************************************************!*\
  !*** ./src/app/fpw-app/components/camera/camera.component.html ***!
  \*****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<mat-card>\n  <mat-card-title>{{description}} [TODO upated time]</mat-card-title>\n  <img mat-card-image [src]=\"getCameraURL()\">\n</mat-card>"

/***/ }),

/***/ "./src/app/fpw-app/components/camera/camera.component.scss":
/*!*****************************************************************!*\
  !*** ./src/app/fpw-app/components/camera/camera.component.scss ***!
  \*****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2Zwdy1hcHAvY29tcG9uZW50cy9jYW1lcmEvY2FtZXJhLmNvbXBvbmVudC5zY3NzIn0= */"

/***/ }),

/***/ "./src/app/fpw-app/components/camera/camera.component.ts":
/*!***************************************************************!*\
  !*** ./src/app/fpw-app/components/camera/camera.component.ts ***!
  \***************************************************************/
/*! exports provided: CameraComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "CameraComponent", function() { return CameraComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");


var CameraComponent = /** @class */ (function () {
    function CameraComponent() {
    }
    CameraComponent.prototype.ngOnInit = function () {
    };
    CameraComponent.prototype.getCameraURL = function () {
        if (this.cameraId) {
            return 'api/camera/' + this.cameraId + '/image';
        }
        else {
            return this.cameraURL;
        }
    };
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])(),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", String)
    ], CameraComponent.prototype, "description", void 0);
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])(),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", String)
    ], CameraComponent.prototype, "cameraId", void 0);
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])(),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", String)
    ], CameraComponent.prototype, "cameraURL", void 0);
    CameraComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-camera',
            template: __webpack_require__(/*! ./camera.component.html */ "./src/app/fpw-app/components/camera/camera.component.html"),
            styles: [__webpack_require__(/*! ./camera.component.scss */ "./src/app/fpw-app/components/camera/camera.component.scss")]
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [])
    ], CameraComponent);
    return CameraComponent;
}());



/***/ }),

/***/ "./src/app/fpw-app/components/image-list/image-list.component.html":
/*!*************************************************************************!*\
  !*** ./src/app/fpw-app/components/image-list/image-list.component.html ***!
  \*************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "Camera selected is {{cameraId}}\n<div *ngFor=\"let cameraURL of urls\">\n  <app-camera [cameraURL]=\"cameraURL\"></app-camera>\n</div>"

/***/ }),

/***/ "./src/app/fpw-app/components/image-list/image-list.component.scss":
/*!*************************************************************************!*\
  !*** ./src/app/fpw-app/components/image-list/image-list.component.scss ***!
  \*************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2Zwdy1hcHAvY29tcG9uZW50cy9pbWFnZS1saXN0L2ltYWdlLWxpc3QuY29tcG9uZW50LnNjc3MifQ== */"

/***/ }),

/***/ "./src/app/fpw-app/components/image-list/image-list.component.ts":
/*!***********************************************************************!*\
  !*** ./src/app/fpw-app/components/image-list/image-list.component.ts ***!
  \***********************************************************************/
/*! exports provided: ImageListComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ImageListComponent", function() { return ImageListComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var src_app_shared_api_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! src/app/shared/api.service */ "./src/app/shared/api.service.ts");




var ImageListComponent = /** @class */ (function () {
    function ImageListComponent(activeRoute, apiService) {
        var _this = this;
        this.activeRoute = activeRoute;
        this.apiService = apiService;
        this.dataupdater = function (data) {
            console.log(data);
            _this.urls.push(data);
        };
        this.urls = [];
        console.log('ImageListComponent constructor');
    }
    ImageListComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.cameraId = this.activeRoute.snapshot.params['id'];
        this.activeRoute.params.subscribe(function (params) {
            _this.urls = [];
            _this.cameraId = params.id;
            _this.apiService.cameraImageList(_this.cameraId).subscribe(_this.dataupdater);
        });
    };
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])(),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", String)
    ], ImageListComponent.prototype, "cameraId", void 0);
    ImageListComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-image-list',
            template: __webpack_require__(/*! ./image-list.component.html */ "./src/app/fpw-app/components/image-list/image-list.component.html"),
            styles: [__webpack_require__(/*! ./image-list.component.scss */ "./src/app/fpw-app/components/image-list/image-list.component.scss")]
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [_angular_router__WEBPACK_IMPORTED_MODULE_2__["ActivatedRoute"], src_app_shared_api_service__WEBPACK_IMPORTED_MODULE_3__["ApiService"]])
    ], ImageListComponent);
    return ImageListComponent;
}());



/***/ }),

/***/ "./src/app/fpw-app/components/main-content/main-content.component.html":
/*!*****************************************************************************!*\
  !*** ./src/app/fpw-app/components/main-content/main-content.component.html ***!
  \*****************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div *ngFor=\"let camera of camerainfo\">\n    <app-camera [description]=\"camera.description\" [cameraId]=\"camera.id\"></app-camera>\n</div>"

/***/ }),

/***/ "./src/app/fpw-app/components/main-content/main-content.component.scss":
/*!*****************************************************************************!*\
  !*** ./src/app/fpw-app/components/main-content/main-content.component.scss ***!
  \*****************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2Zwdy1hcHAvY29tcG9uZW50cy9tYWluLWNvbnRlbnQvbWFpbi1jb250ZW50LmNvbXBvbmVudC5zY3NzIn0= */"

/***/ }),

/***/ "./src/app/fpw-app/components/main-content/main-content.component.ts":
/*!***************************************************************************!*\
  !*** ./src/app/fpw-app/components/main-content/main-content.component.ts ***!
  \***************************************************************************/
/*! exports provided: MainContentComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MainContentComponent", function() { return MainContentComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var src_app_shared_api_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! src/app/shared/api.service */ "./src/app/shared/api.service.ts");



var MainContentComponent = /** @class */ (function () {
    function MainContentComponent(apiService) {
        this.apiService = apiService;
    }
    MainContentComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.apiService.camerainfo().subscribe(function (data) {
            console.log(data);
            _this.camerainfo = data;
        }, function (err) {
            console.error("error getting camera list: " + err);
        });
    };
    MainContentComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            template: __webpack_require__(/*! ./main-content.component.html */ "./src/app/fpw-app/components/main-content/main-content.component.html"),
            styles: [__webpack_require__(/*! ./main-content.component.scss */ "./src/app/fpw-app/components/main-content/main-content.component.scss")]
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [src_app_shared_api_service__WEBPACK_IMPORTED_MODULE_2__["ApiService"]])
    ], MainContentComponent);
    return MainContentComponent;
}());



/***/ }),

/***/ "./src/app/fpw-app/components/sidenav/sidenav.component.html":
/*!*******************************************************************!*\
  !*** ./src/app/fpw-app/components/sidenav/sidenav.component.html ***!
  \*******************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<mat-sidenav-container class=\"app-sidenav-container\">\n    <mat-sidenav #sidenav class=\"app-sidenav mat-elevation-z10\" [opened]=\"!isScreenSmall()\"\n        [mode]=\"isScreenSmall() ? 'over' : 'side'\">\n        <mat-toolbar color=\"primary\">\n            Menu\n        </mat-toolbar>\n\n        <mat-nav-list>\n            <mat-list-item (click)=\"clickAllCameras()\">\n                <mat-icon matSuffix>camera_enhance</mat-icon>\n                All Cameras\n            </mat-list-item>\n        </mat-nav-list>\n\n        <mat-nav-list *ngFor=\"let camera of camerainfo\">\n            <mat-list-item (click)=\"clickCamera(camera.id)\">\n                <mat-icon matSuffix>camera</mat-icon>\n                {{camera.description}}\n            </mat-list-item>\n        </mat-nav-list>\n\n    </mat-sidenav>\n\n    <div class=\"app-sidenav-content\">\n        <app-toolbar (toggleSidenav)=\"sidenav.toggle()\" >\n        </app-toolbar>\n        <div class=\"wrapper\">\n            <router-outlet></router-outlet>\n        </div>\n    </div>\n\n</mat-sidenav-container>"

/***/ }),

/***/ "./src/app/fpw-app/components/sidenav/sidenav.component.scss":
/*!*******************************************************************!*\
  !*** ./src/app/fpw-app/components/sidenav/sidenav.component.scss ***!
  \*******************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".app-sidenav-container {\n  flex: 1;\n  position: fixed;\n  width: 100%;\n  min-width: 100%;\n  height: 100%;\n  min-height: 100%; }\n\n.app-sidenav-content {\n  display: flex;\n  height: 100%;\n  flex-direction: column; }\n\n.app-sidenav {\n  width: 240px; }\n\n.wrapper {\n  margin: 50px; }\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIi9ob21lL2RhZC9zcmMvZnJvemVuLXBhcnRpY2xlLXdhdmVzL3dlYi1hcHAvc3JjL2FwcC9mcHctYXBwL2NvbXBvbmVudHMvc2lkZW5hdi9zaWRlbmF2LmNvbXBvbmVudC5zY3NzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBO0VBQ0ksT0FBTztFQUNQLGVBQWU7RUFDZixXQUFXO0VBQ1gsZUFBZTtFQUNmLFlBQVk7RUFDWixnQkFBZ0IsRUFBQTs7QUFHbEI7RUFDRSxhQUFhO0VBQ2IsWUFBWTtFQUNaLHNCQUFzQixFQUFBOztBQUd4QjtFQUNFLFlBQVksRUFBQTs7QUFHZDtFQUNFLFlBQVksRUFBQSIsImZpbGUiOiJzcmMvYXBwL2Zwdy1hcHAvY29tcG9uZW50cy9zaWRlbmF2L3NpZGVuYXYuY29tcG9uZW50LnNjc3MiLCJzb3VyY2VzQ29udGVudCI6WyIuYXBwLXNpZGVuYXYtY29udGFpbmVyIHtcbiAgICBmbGV4OiAxO1xuICAgIHBvc2l0aW9uOiBmaXhlZDtcbiAgICB3aWR0aDogMTAwJTtcbiAgICBtaW4td2lkdGg6IDEwMCU7XG4gICAgaGVpZ2h0OiAxMDAlO1xuICAgIG1pbi1oZWlnaHQ6IDEwMCU7XG4gIH1cbiAgXG4gIC5hcHAtc2lkZW5hdi1jb250ZW50IHtcbiAgICBkaXNwbGF5OiBmbGV4O1xuICAgIGhlaWdodDogMTAwJTtcbiAgICBmbGV4LWRpcmVjdGlvbjogY29sdW1uO1xuICB9XG4gIFxuICAuYXBwLXNpZGVuYXYge1xuICAgIHdpZHRoOiAyNDBweDtcbiAgfVxuICBcbiAgLndyYXBwZXIge1xuICAgIG1hcmdpbjogNTBweDtcbiAgfVxuICAgICJdfQ== */"

/***/ }),

/***/ "./src/app/fpw-app/components/sidenav/sidenav.component.ts":
/*!*****************************************************************!*\
  !*** ./src/app/fpw-app/components/sidenav/sidenav.component.ts ***!
  \*****************************************************************/
/*! exports provided: SidenavComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SidenavComponent", function() { return SidenavComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm5/material.es5.js");
/* harmony import */ var src_app_shared_api_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! src/app/shared/api.service */ "./src/app/shared/api.service.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");





var SMALL_WIDTH_BREAKPOINT = 720;
var SidenavComponent = /** @class */ (function () {
    function SidenavComponent(zone, apiService, router, activatedRoute) {
        this.zone = zone;
        this.apiService = apiService;
        this.router = router;
        this.activatedRoute = activatedRoute;
        this.mediaMatcher = matchMedia("(max-width: " + SMALL_WIDTH_BREAKPOINT + "px)");
    }
    SidenavComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.apiService.camerainfo().subscribe(function (data) {
            console.log(data);
            _this.camerainfo = data;
        }, function (err) {
            console.error("error getting camera list: " + err);
        });
    };
    SidenavComponent.prototype.clickAllCameras = function () {
        this.router.navigate(['all'], { relativeTo: this.activatedRoute });
    };
    SidenavComponent.prototype.clickCamera = function (cameraId) {
        console.log("clickCamera()");
        console.log(cameraId);
        this.router.navigate(['image-list', cameraId], { relativeTo: this.activatedRoute });
    };
    SidenavComponent.prototype.isScreenSmall = function () {
        return this.mediaMatcher.matches;
    };
    SidenavComponent.prototype.toggleSideNav = function () {
        this.sidenav.toggle();
    };
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ViewChild"])(_angular_material__WEBPACK_IMPORTED_MODULE_2__["MatSidenav"]),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", _angular_material__WEBPACK_IMPORTED_MODULE_2__["MatSidenav"])
    ], SidenavComponent.prototype, "sidenav", void 0);
    SidenavComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-sidenav',
            template: __webpack_require__(/*! ./sidenav.component.html */ "./src/app/fpw-app/components/sidenav/sidenav.component.html"),
            styles: [__webpack_require__(/*! ./sidenav.component.scss */ "./src/app/fpw-app/components/sidenav/sidenav.component.scss")]
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [_angular_core__WEBPACK_IMPORTED_MODULE_1__["NgZone"],
            src_app_shared_api_service__WEBPACK_IMPORTED_MODULE_3__["ApiService"],
            _angular_router__WEBPACK_IMPORTED_MODULE_4__["Router"],
            _angular_router__WEBPACK_IMPORTED_MODULE_4__["ActivatedRoute"]])
    ], SidenavComponent);
    return SidenavComponent;
}());



/***/ }),

/***/ "./src/app/fpw-app/components/toolbar/toolbar.component.html":
/*!*******************************************************************!*\
  !*** ./src/app/fpw-app/components/toolbar/toolbar.component.html ***!
  \*******************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<mat-toolbar color=\"primary\">\n\n  <button mat-button class=\"sidenav-toggle\" (click)=\"toggleSidenav.emit()\">\n    <mat-icon>menu</mat-icon>\n  </button>\n\n  <span>Frozen Particle Waves</span>\n\n  <span class=\"example-spacer\"></span>\n<!--\n  <button mat-button [matMenuTriggerFor]=\"menu\">\n    <mat-icon>more_vert</mat-icon>\n  </button>\n  <mat-menu #menu=\"matMenu\">\n    <button mat-menu-item (click)=\"openAddContactDialog()\">New Contact</button>\n    <button mat-menu-item (click)=\"toggleTheme.emit()\">Toggle theme</button>\n    <button mat-menu-item (click)=\"toggleDir.emit()\">Toggle dir</button>\n  </mat-menu>\n-->\n\n</mat-toolbar>"

/***/ }),

/***/ "./src/app/fpw-app/components/toolbar/toolbar.component.scss":
/*!*******************************************************************!*\
  !*** ./src/app/fpw-app/components/toolbar/toolbar.component.scss ***!
  \*******************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2Zwdy1hcHAvY29tcG9uZW50cy90b29sYmFyL3Rvb2xiYXIuY29tcG9uZW50LnNjc3MifQ== */"

/***/ }),

/***/ "./src/app/fpw-app/components/toolbar/toolbar.component.ts":
/*!*****************************************************************!*\
  !*** ./src/app/fpw-app/components/toolbar/toolbar.component.ts ***!
  \*****************************************************************/
/*! exports provided: ToolbarComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ToolbarComponent", function() { return ToolbarComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");



var ToolbarComponent = /** @class */ (function () {
    function ToolbarComponent() {
        this.toggleSidenav = new _angular_core__WEBPACK_IMPORTED_MODULE_1__["EventEmitter"]();
    }
    ToolbarComponent.prototype.ngOnInit = function () {
    };
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Output"])(),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", Object)
    ], ToolbarComponent.prototype, "toggleSidenav", void 0);
    ToolbarComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-toolbar',
            template: __webpack_require__(/*! ./toolbar.component.html */ "./src/app/fpw-app/components/toolbar/toolbar.component.html"),
            styles: [__webpack_require__(/*! ./toolbar.component.scss */ "./src/app/fpw-app/components/toolbar/toolbar.component.scss")]
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [])
    ], ToolbarComponent);
    return ToolbarComponent;
}());



/***/ }),

/***/ "./src/app/fpw-app/fpw-app.component.ts":
/*!**********************************************!*\
  !*** ./src/app/fpw-app/fpw-app.component.ts ***!
  \**********************************************/
/*! exports provided: FpwAppComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "FpwAppComponent", function() { return FpwAppComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");


var FpwAppComponent = /** @class */ (function () {
    function FpwAppComponent() {
    }
    FpwAppComponent.prototype.ngOnInit = function () {
    };
    FpwAppComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-fpw-app',
            template: "\n    <p>\n      <app-sidenav></app-sidenav>\n    </p>\n  "
        }),
        tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [])
    ], FpwAppComponent);
    return FpwAppComponent;
}());



/***/ }),

/***/ "./src/app/fpw-app/fpw.module.ts":
/*!***************************************!*\
  !*** ./src/app/fpw-app/fpw.module.ts ***!
  \***************************************/
/*! exports provided: FpwModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "FpwModule", function() { return FpwModule; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _angular_flex_layout__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/flex-layout */ "./node_modules/@angular/flex-layout/esm5/flex-layout.es5.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _components_toolbar_toolbar_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./components/toolbar/toolbar.component */ "./src/app/fpw-app/components/toolbar/toolbar.component.ts");
/* harmony import */ var _components_sidenav_sidenav_component__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ./components/sidenav/sidenav.component */ "./src/app/fpw-app/components/sidenav/sidenav.component.ts");
/* harmony import */ var _components_main_content_main_content_component__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ./components/main-content/main-content.component */ "./src/app/fpw-app/components/main-content/main-content.component.ts");
/* harmony import */ var _fpw_app_component__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! ./fpw-app.component */ "./src/app/fpw-app/fpw-app.component.ts");
/* harmony import */ var _shared_material_module__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! ../shared/material.module */ "./src/app/shared/material.module.ts");
/* harmony import */ var _components_image_list_image_list_component__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! ./components/image-list/image-list.component */ "./src/app/fpw-app/components/image-list/image-list.component.ts");
/* harmony import */ var _components_camera_camera_component__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! ./components/camera/camera.component */ "./src/app/fpw-app/components/camera/camera.component.ts");













var routes = [
    {
        path: '', component: _fpw_app_component__WEBPACK_IMPORTED_MODULE_9__["FpwAppComponent"],
        children: [
            { path: '', component: _components_main_content_main_content_component__WEBPACK_IMPORTED_MODULE_8__["MainContentComponent"] },
            { path: 'all', component: _components_main_content_main_content_component__WEBPACK_IMPORTED_MODULE_8__["MainContentComponent"] },
            { path: 'image-list/:id', component: _components_image_list_image_list_component__WEBPACK_IMPORTED_MODULE_11__["ImageListComponent"], }
        ]
    }
];
var FpwModule = /** @class */ (function () {
    function FpwModule() {
    }
    FpwModule = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["NgModule"])({
            declarations: [
                _components_toolbar_toolbar_component__WEBPACK_IMPORTED_MODULE_6__["ToolbarComponent"],
                _components_sidenav_sidenav_component__WEBPACK_IMPORTED_MODULE_7__["SidenavComponent"],
                _components_main_content_main_content_component__WEBPACK_IMPORTED_MODULE_8__["MainContentComponent"],
                _fpw_app_component__WEBPACK_IMPORTED_MODULE_9__["FpwAppComponent"],
                _components_image_list_image_list_component__WEBPACK_IMPORTED_MODULE_11__["ImageListComponent"],
                _components_camera_camera_component__WEBPACK_IMPORTED_MODULE_12__["CameraComponent"]
            ],
            imports: [
                _angular_common__WEBPACK_IMPORTED_MODULE_2__["CommonModule"],
                _shared_material_module__WEBPACK_IMPORTED_MODULE_10__["MaterialModule"],
                _angular_flex_layout__WEBPACK_IMPORTED_MODULE_4__["FlexLayoutModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_5__["FormsModule"],
                _angular_router__WEBPACK_IMPORTED_MODULE_3__["RouterModule"].forChild(routes)
            ]
        })
    ], FpwModule);
    return FpwModule;
}());



/***/ })

}]);
//# sourceMappingURL=fpw-app-fpw-module.js.map