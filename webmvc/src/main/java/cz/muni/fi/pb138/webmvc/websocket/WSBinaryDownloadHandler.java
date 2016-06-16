package cz.muni.fi.pb138.webmvc.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

/**
 * Created by Michal Holic on 12.06.2016
 */
public class WSBinaryDownloadHandler extends BinaryWebSocketHandler {

	private static final Logger log = LoggerFactory.getLogger(WSBinaryDownloadHandler.class);

	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
		String uri = session.getUri().getPath().replaceFirst("/websocket/binary/download/", "");
		log.debug("Requested binary download to frontend of {}.", uri);

		//TODO

		session.close(new CloseStatus(1000, "Finished processing."));
	}
}
