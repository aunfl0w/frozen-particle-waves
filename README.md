# frozen-particle-waves
See the file LICENSE for license.
Frozen particle waves is a starting place to add IoT type monitoring for cameras devices etc.  

## First Features
The first goal is to get a working camera monitoring framework.  This will capture images from various 
network cameras in a flexible and extensible manner.  A minimally functional UI needs to be added as well.

## Architecture
One of the other goals is to provide a learning project for spring boot.  The UI will also likely be built with Angular.

## How it may be used
The goal is that you could run this on a computer in a location that has network devices that you configure it 
to gather information from.  It would then provide a consolidate view of that information and provide additional 
features such as storing data, showing trends.

### How to build and run
You will need the pre-requisites to build and run. 

1. java 8 jdk
2. maven (mvn)

#### Build Configure and Run
1. Clone the repo git clone https://github.com/aunfl0w/frozen-particle-waves.git
2. Run mvn install to build/test and make an example config file in src/test/resources/ImageRetrievers.xml 
3. Run mvn install to make the spring boot app jar
4. Copy the ImageRetrievers.xml file to another location and customize it as needed.
5. Start the application with this command
	java -jar target/frozen-particle-waves-0.0.1-SNAPSHOT.jar --camera.config.file=<full path to your file>


## TODOs
1. UI [](https://github.com/aunfl0w/frozen-particle-waves-gui "frozen-particle-waves-gui") 
2. Get minimal working app.
* Support Basic Auth Web Cameras
* Run as proxy to cameras of existing system



