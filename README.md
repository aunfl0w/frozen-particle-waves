# frozen-particle-waves
See the file LICENSE for license.
Frozen particle waves is a security and aggregation front and end for IoT devices such as cameras and AI Image processing.  


## Goals
* Learning project for spring boot.  
* Learning project for client side UI.
* IoT hardware learning.

## How it may be used
Run FPW in a network with IoT devices to centralize data and coordinate access and distribution.

#### Build Configure and Run
1. Clone the repo git clone https://github.com/aunfl0w/frozen-particle-waves.git
2. Run mvn install to build/test and make an example config file in src/test/resources/ImageRetrievers.xml 
3. Run mvn install to make the spring boot app jar
4. Copy the ImageRetrievers.xml file to another location and customize it as needed.
5. Start the application with this command
	java -jar target/frozen-particle-waves-0.0.1-SNAPSHOT.jar --camera.config.file=<full path to your file>


