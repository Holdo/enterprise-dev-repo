package cz.muni.fi.pb138.webmvc.websocket;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pb138.webmvc.websocket.exception.UnknownWebSocketCommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

/**
 * Created by Michal Holic on 15/06/2016
 */
public class WsCommandsHandler extends TextWebSocketHandler {

	private static final Logger log = LoggerFactory.getLogger(WsCommandsHandler.class);

	@Autowired
	private WsCommands wsCommands;

	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.trace("Websocket session {} connected.", session.getId());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.trace("Websocket session {} closed. Reason: {}", session.getId(), status.getReason());
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String command = session.getUri().getPath().replaceFirst("/websocket/command/", "");
		log.debug("Handling {} command from frontend.", command);
		Map<String, Object> args = mapper.readValue(message.getPayload(), new TypeReference<Map<String,Object>>(){});
		Object returnedObject = execute(command, args);
		String json = mapper.writeValueAsString(returnedObject);
		session.sendMessage(new TextMessage(json));
		session.close(new CloseStatus(1000, "Finished processing."));
	}

	private Object execute(String command, Map<String, Object> args) throws UnknownWebSocketCommandException {
		Object returnedObject;
		try {
			returnedObject = wsCommands.getClass().getMethod(command, Map.class).invoke(wsCommands, args);
		} catch (Exception e) {
			UnknownWebSocketCommandException uwsce = new UnknownWebSocketCommandException("Command " + command + " does not exist.", e);
			log.error("Exception during websocket command execution handling.", uwsce);
			throw uwsce;
		}
		log.debug("Returning object " + returnedObject);
		return returnedObject;
	}

}
