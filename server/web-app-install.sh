#!/bin/bash

echo STARTING ANGULAR UI WEB APP BUILD AND INSTALL
cd ../web-app
npm install
ng build --prod --build-optimizer
cp dist/web-app/* ../server/src/main/resources/resources/
echo COMPLETED ANGULAR UI WEB APP BUILD AND INSTALL
