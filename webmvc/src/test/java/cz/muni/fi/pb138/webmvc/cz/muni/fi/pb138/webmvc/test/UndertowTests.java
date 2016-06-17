package cz.muni.fi.pb138.webmvc.cz.muni.fi.pb138.webmvc.test;

import cz.muni.fi.pb138.webmvc.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by Michal Holic on 10/06/2016
 */
public class UndertowTests extends AbstractIntegrationTest {

	@Test
	public void testHome() throws Exception {
		assertOkResponse("/hello", "Hello World!");
	}

	@Test
	public void testAsync() throws Exception {
		assertOkResponse("/async", "async: Hello World!");
	}

	private void assertOkResponse(String path, String body) {
		ResponseEntity<String> entity = new TestRestTemplate().getForEntity("http://localhost:8080" + path, String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isEqualTo(body);
	}
}
