package cz.muni.fi.pb138.webmvc.controller;

import cz.muni.fi.pb138.service.FileServiceImpl;
import cz.muni.fi.pb138.api.FileService;
//import static org.basex.util.Util.message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private RetrievedFileMap retrievedFileMap;

    /**
     *
     * @param binaryMessage
     * @throws Exception
     */
    @MessageMapping("/binaryFile")
    public void createUpdate(Message<byte[]> binaryMessage) throws Exception {

        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(binaryMessage, StompHeaderAccessor.class);
        String fullPath = (String) headerAccessor.getMessageHeaders().get("fileName");
        FileService fileService = new FileServiceImpl();
        fileService.saveFile(fullPath, binaryMessage.getPayload());
    }
}
