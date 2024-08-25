FROM ubuntu:24.04
RUN useradd -d /home/fpw -s /bin/bash -u 1001 -U fpw \
	&& mkdir /home/fpw \
	&& mkdir /home/fpw/bin \
	&& chown fpw:fpw /home/fpw \
	&& chown fpw:fpw /home/fpw/bin \
	&& apt update \
	&& apt install -y --no-install-recommends openjdk-21-jre-headless curl \
	&& apt install -y mencoder \
	&& apt clean 


VOLUME 	/tmp
VOLUME 	/home/fpw

EXPOSE 8080
ADD --chown=root:root ./java.21.security /etc/java-21-openjdk/security/java.security
ADD --chown=fpw:fpw target/frozen-particle-waves-0.0.1-SNAPSHOT.jar /home/fpw/bin/ 
ADD --chown=fpw:fpw bin.docker/ /home/fpw/bin/

ENTRYPOINT su --login fpw --whitelist-environment=DATA_PATH,STANDARD_PASS,MQTT_SERVER --command "/bin/bash -c ~fpw/bin/startfpw"

	


