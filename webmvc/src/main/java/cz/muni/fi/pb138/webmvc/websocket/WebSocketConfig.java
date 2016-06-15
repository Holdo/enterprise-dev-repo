
package cz.muni.fi.pb138.webmvc.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(wsBinaryFileHandler(), "/websocket/binary/**")
				.addHandler(wsCommandsHandler(), "websocket/command/*");
				//.addInterceptors(new HttpSessionHandshakeInterceptor());
	}

	@Bean
	public WebSocketHandler wsBinaryFileHandler() {
		return new WSBinaryFileHandler();
	}

	@Bean
	public WebSocketHandler wsCommandsHandler() {
		return new WsCommandsHandler();
	}

}