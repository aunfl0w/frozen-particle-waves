package fpw.domain;

import java.util.HashMap;

public class ClientCommunication {

	String messageType = "1"; 
	String updateCamera;
	HashMap<String, String> values = new HashMap<>();

	public HashMap<String, String> getRecognitions(){
		return values;
	}
	
	public void setRecognitions(HashMap<String, String> values){
		this.values = values;
	}
	
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getUpdateCamera() {
		return updateCamera;
	}
	public void setUpdateCamera(String updateCamera) {
		this.updateCamera = updateCamera;
	}
	
}
