package fpw.events;

import lombok.Data;

@Data
public class ClientCommunication {

	String messageType = "1";
	String updateCamera;
	Long imageID;
}
