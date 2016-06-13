package cz.muni.fi.pb138.webmvc;

import cz.muni.fi.pb138.webmvc.websocket.WebSocketConfig;
import org.basex.BaseXServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import javax.annotation.PreDestroy;
import java.io.IOException;

@SpringBootApplication(scanBasePackages = {"cz.muni.fi.pb138"})
@Import(WebSocketConfig.class)
public class RepositoryApplication {

	public static void main(String[] args) throws Exception {
		//Start the DB first
		BaseXServer.main(new String[]{});
		//Start Spring container
		SpringApplication.run(RepositoryApplication.class, args);
	}

	@PreDestroy
	public static void tearDown() throws IOException {
		BaseXServer.stop("localhost", 1984);
	}
}
