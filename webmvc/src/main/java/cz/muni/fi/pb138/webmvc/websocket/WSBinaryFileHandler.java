package cz.muni.fi.pb138.webmvc.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

/**
 * Created by Michal Holic on 12.06.2016
 */
public class WSBinaryFileHandler extends BinaryWebSocketHandler {

	private static final Logger log = LoggerFactory.getLogger(WSBinaryFileHandler.class);

	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
		log.info("Received binary message from frontend with length: " + message.getPayloadLength());
		log.info("URI: " + session.getUri());
	}
}
