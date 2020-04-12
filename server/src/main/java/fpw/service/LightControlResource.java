package fpw.service;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpw.domain.mqtt.LightCommand;
import fpw.domain.mqtt.MQTTClientComponent;

@RestController
@RequestMapping("api/")
public class LightControlResource {
	private static final Logger log = LoggerFactory.getLogger(LightControlResource.class);
	
	@Autowired
	MQTTClientComponent mqttClient;
	
	@PostMapping("light/{lightID}/command")
	public void postLight(@PathVariable String lightID, @RequestBody LightCommand command) throws MqttException {
		log.info("Command is for " + lightID + " command " + command);
		mqttClient.doCommand(command);
	}
	
	
	
	
	
}
