package cz.muni.fi.pb138.webmvc;


import cz.muni.fi.pb138.webmvc.controller.WebSocketConfig;
import org.basex.BaseXServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = {"cz.muni.fi.pb138"})
@Import(WebSocketConfig.class)
public class UndertowApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(UndertowApplication.class, args);
		BaseXServer.main(new String[]{});
	}
}
