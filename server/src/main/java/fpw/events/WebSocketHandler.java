package fpw.events;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class WebSocketHandler extends TextWebSocketHandler implements ApplicationListener<ClientCommunicationEvent> {
	private List<WebSocketSession> sessions = new ArrayList<>();

	@Override
	public void onApplicationEvent(ClientCommunicationEvent event) {
		String message = makeMessage(event);
		TextMessage txm = new TextMessage(message);
		sessions.forEach((session) -> {
			try {
				session.sendMessage(txm);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

	}

	String makeMessage(ClientCommunicationEvent event) {
		ObjectMapper mapper = new ObjectMapper();
		String message;
		try {
			message = mapper.writeValueAsString(event.getMessage());
		} catch (JsonProcessingException e) {
			message = null;
		}
		return message;
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		sessions.add(session);
		log.info("Adding websocket {}", session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		sessions.remove(session);
		log.info("Removing websocket {}", session);
	}

}
