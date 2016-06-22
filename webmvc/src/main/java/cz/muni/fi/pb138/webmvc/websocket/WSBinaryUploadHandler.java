package cz.muni.fi.pb138.webmvc.websocket;

import cz.muni.fi.pb138.api.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

/**
 * @author Michal Holič
 */
public class WSBinaryUploadHandler extends BinaryWebSocketHandler {

	private static final Logger log = LoggerFactory.getLogger(WSBinaryUploadHandler.class);

	@Autowired
	private FileService fileService;

	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
		String uri = session.getUri().getPath().replaceFirst("/websocket/binary/upload/", "");
		log.debug("Received binary upload from frontend with length {} and path {}.", message.getPayloadLength(), uri);
		byte[] receivedBinaryFile = new byte[message.getPayloadLength()];
		message.getPayload().get(receivedBinaryFile);
		fileService.saveFile(uri, receivedBinaryFile);
		session.close(new CloseStatus(1000, "Finished processing."));
	}
}
