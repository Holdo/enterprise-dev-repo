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
public class WSBinaryDownloadHandler extends BinaryWebSocketHandler {

	private static final Logger log = LoggerFactory.getLogger(WSBinaryDownloadHandler.class);

	@Autowired
	private FileService fileService;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String uri = session.getUri().getPath().replaceFirst("/websocket/binary/download/", "");
		int indexOfFirstSlash = uri.indexOf('/');
		int version = Integer.parseInt(uri.substring(0, indexOfFirstSlash));
		uri = uri.substring(indexOfFirstSlash + 1);
		log.debug("Requested binary download to frontend of {} of version {}", uri, version);
		session.sendMessage(new BinaryMessage(fileService.getFileByFullPathAndVersion(uri, version)));
		session.close(new CloseStatus(1000, "Finished processing."));
	}
}
