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
        String sessionId = headerAccessor.getSessionId();

        if (retrievedFileMap.getMyMap().containsKey(sessionId)) {
            retrievedFileMap.getMyMap().get(sessionId).setBinaryMessage(binaryMessage.getPayload());
        } else {
            RetrievedFile retrievedFile = new RetrievedFile();
            retrievedFile.setBinaryMessage(binaryMessage.getPayload());
            retrievedFileMap.getMyMap().put(sessionId, retrievedFile);
        }

        if (retrievedFileMap.getMyMap().get(sessionId).getName() != null) {
            FileService fileService = new FileServiceImpl();
            String name = retrievedFileMap.getMyMap().get(sessionId).getName();
            String[] splitedName = name.split(":");
            fileService.saveFile(name, binaryMessage.getPayload());
        }
    }

    @MessageMapping("/fileName")
    public void createUpdateName(Message<String> fileName) throws Exception {

        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(fileName, StompHeaderAccessor.class);
        String sessionId = headerAccessor.getSessionId();

        if (retrievedFileMap.getMyMap().containsKey(sessionId)) {
            retrievedFileMap.getMyMap().get(sessionId).setName(fileName.getPayload());
        } else {
            RetrievedFile retrievedFile = new RetrievedFile();
            retrievedFile.setName(fileName.getPayload());
            retrievedFileMap.getMyMap().put(sessionId, retrievedFile);
        }

        if (retrievedFileMap.getMyMap().get(sessionId).getBinaryMessage().length != 0) {
            FileService fileService = new FileServiceImpl();
            byte[] binaryMessage = retrievedFileMap.getMyMap().get(sessionId).getBinaryMessage();
            String[] fileNameSplited = fileName.getPayload().split(":");
            fileService.saveFile(fileNameSplited[0], binaryMessage);
        }
    }

}
