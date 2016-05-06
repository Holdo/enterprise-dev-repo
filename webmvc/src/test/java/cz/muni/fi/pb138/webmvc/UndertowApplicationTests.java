package cz.muni.fi.pb138.webmvc;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;

@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = UndertowApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
public class UndertowApplicationTests {

	@Test
	public void testHome() throws Exception {
		assertOkResponse("/", "Hello World!");
	}

	@Test
	public void testAsync() throws Exception {
		assertOkResponse("/async", "async: Hello World!");
	}

	@Test
	public void testCompression() throws Exception {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("Accept-Encoding", "gzip");
		HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);
		RestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<byte[]> entity = restTemplate.exchange("http://localhost:8080", HttpMethod.GET, requestEntity, byte[].class);
		Assert.assertEquals(entity.getStatusCode(), HttpStatus.OK);
		try (GZIPInputStream inflater = new GZIPInputStream(new ByteArrayInputStream(entity.getBody()))) {
			Assert.assertEquals(StreamUtils.copyToString(inflater, Charset.forName("UTF-8")),"Hello World!");
		}
	}

	private void assertOkResponse(String path, String body) {
		ResponseEntity<String> entity = new TestRestTemplate().getForEntity("http://localhost:8080" + path, String.class);
		Assert.assertEquals(entity.getStatusCode(), HttpStatus.OK);
		Assert.assertEquals(entity.getBody(), body);
	}
}
