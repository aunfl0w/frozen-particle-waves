package fpw.domain;

public class ClientCommunication {

	String messageType = "1"; 
	String updateCamera;

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
