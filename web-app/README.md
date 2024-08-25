# Frozen Particle Waves Web App

This is the Frozen Particle Waves Angular web frontend.  If you are running the FPW SpringBoot application on the same host one port 8080, the default port for the app, the proxy.conf.json for webpack proxy is already configured to make use of that as your server.  You can use the Development server and other commands below in that case.  This application is designed to be packaged and served from the resources of the SpringBoot Web app that hosts the services that the Angular UI depends on.  To package and run that application see the scripts in the ../server directory.


## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).
