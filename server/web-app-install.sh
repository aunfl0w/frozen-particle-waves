#!/bin/bash

echo STARTING ANGULAR UI WEB APP BUILD AND INSTALL
cd ..
rm server/src/main/resources/resources/es2015-polyfills*
rm server/src/main/resources/resources/favicon.ico
rm server/src/main/resources/resources/fpw-app-fpw-module*
rm server/src/main/resources/resources/index.html
rm server/src/main/resources/resources/main*
rm server/src/main/resources/resources/polyfills* 
rm server/src/main/resources/resources/runtime*
rm server/src/main/resources/resources/styles*
rm server/src/main/resources/resources/vendor*
rm server/src/main/resources/resources/#*.js

cd web-app
npm install
ng build --prod --build-optimizer
cp dist/web-app/* ../server/src/main/resources/resources/
echo COMPLETED ANGULAR UI WEB APP BUILD AND INSTALL
