package cz.muni.fi.pb138.websocketapi;

import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author root
 */
@ServerEndpoint("/endpoint")
public class NewWSEndpoint {

    @OnMessage
    public String onMessage(String message) {
        return null;
    }
    
}
