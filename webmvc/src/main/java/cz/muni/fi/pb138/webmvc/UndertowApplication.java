package cz.muni.fi.pb138.webmvc;


import org.basex.BaseXServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"cz.muni.fi.pb138"})
public class UndertowApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(UndertowApplication.class, args);
		BaseXServer.main(new String[]{});
	}
}
