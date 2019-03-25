package fpw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import fpw.domain.ClientCommunication;

@Controller
public class WebSocketNotifier {
	private SimpMessagingTemplate template;

	@Autowired
	public WebSocketNotifier(SimpMessagingTemplate template) {
		this.template = template;
	}

	@SendTo("/queue/comms")
	public void announceUpdate(String cameraID, Long imageID) {
		ClientCommunication cc = new ClientCommunication();
		cc.setUpdateCamera(cameraID);
		cc.setImageID(imageID);
		template.convertAndSend("/queue/comms", cc);
	}
	
}
