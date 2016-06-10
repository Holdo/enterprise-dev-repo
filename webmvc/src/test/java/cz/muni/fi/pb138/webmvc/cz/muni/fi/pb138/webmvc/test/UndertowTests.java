package cz.muni.fi.pb138.webmvc.cz.muni.fi.pb138.webmvc.test;

import cz.muni.fi.pb138.webmvc.IntegrationTests;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;

/**
 * Created by Michal Holic on 10/06/2016
 */
public class UndertowTests extends IntegrationTests {

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
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<byte[]> entity = restTemplate.exchange("http://localhost:8080", HttpMethod.GET, requestEntity, byte[].class);
		Assert.assertEquals(entity.getStatusCode(), HttpStatus.OK);
		try (GZIPInputStream inflater = new GZIPInputStream(new ByteArrayInputStream(entity.getBody()))) {
			Assert.assertEquals(StreamUtils.copyToString(inflater, Charset.forName("UTF-8")), "Hello World!");
		}
	}

	private void assertOkResponse(String path, String body) {
		ResponseEntity<String> entity = new TestRestTemplate().getForEntity("http://localhost:8080" + path, String.class);
		Assert.assertEquals(entity.getStatusCode(), HttpStatus.OK);
		Assert.assertEquals(entity.getBody(), body);
	}
}
