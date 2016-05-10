package cz.muni.fi.pb138.webmvc.controller;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {


    @MessageMapping("/hello")
    public String greeting(String message) throws Exception {
        return message;
    }

}
