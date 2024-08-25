package fpw.events;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@SuppressWarnings("serial")
public class ClientCommunicationEvent extends ApplicationEvent {
	@Getter
	ClientCommunication message;

	public ClientCommunicationEvent(Object source, ClientCommunication message) {
		super(source);
		this.message = message;
				
	}

}
