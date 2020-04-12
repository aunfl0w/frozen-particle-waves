package fpw.domain.mqtt;

import java.util.UUID;

import javax.annotation.PreDestroy;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MQTTClientComponent {
	private static final Logger log = LoggerFactory.getLogger(MQTTClientComponent.class);
	MqttClient client;
	String server;

	@Autowired
	public MQTTClientComponent(@Value("${things.mqtt.server}") String server) {
		this.server = server;
		setup();
	}

	synchronized void setup() {
		try {
			if (this.client != null) {
				preDestroy();
			}

			client = new MqttClient(server, "fpw-client-" + UUID.randomUUID().toString());
			client.connect();
			log.info("Connected to mqtt server " + server);

		} catch (Exception e) {
			log.error("mqttClient exception connecting to " + server, e);
		}
	}

	public void doCommand(LightCommand command) throws MqttException {
		if (client == null || !client.isConnected())
			setup();

		client.publish("house/light/cmnd/light1/" + command.getCommandName(),
				new MqttMessage(command.getCommandValue().getBytes()));
	}

	@PreDestroy()
	public void preDestroy() {
		try {
			client.disconnect();
			client.close();
		} catch (MqttException e) {
			log.error("mqttClient destroy exception", e);
		}
	}

}
