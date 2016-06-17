package cz.muni.fi.pb138.webmvc.cz.muni.fi.pb138.webmvc.test;

import cz.muni.fi.pb138.api.FileService;
import cz.muni.fi.pb138.dao.DatabaseDao;
import cz.muni.fi.pb138.webmvc.AbstractIntegrationTest;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by gasior on 15.05.2016
 */
public class DatabaseIOTests extends AbstractIntegrationTest {

	@Autowired
	private FileService fileService;

	@Autowired
	private DatabaseDao databaseDao;

	@Before
	public void createDatabase() throws IOException {
		databaseDao.createDatabase(XML_DATABASE_NAME);
		databaseDao.createDatabase(META_DATABASE_NAME);
	}

	@After
	public void dropDatabase() throws IOException {
		databaseDao.dropDatabase(XML_DATABASE_NAME);
		databaseDao.dropDatabase(META_DATABASE_NAME);
	}

	@Test
	public void IOXsdFileTest() throws Exception {
		byte[] file = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.xsd"));
		fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file);
		byte[] readFile = fileService.getFileByFullPath("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");

		assertThat(file).isEqualTo(readFile);
	}

	@Test
	public void IOWsdlFileTest() throws Exception {
		byte[] file = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.wsdl"));
		fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.wsdl", file);
		byte[] readFile = fileService.getFileByFullPath("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.wsdl");

		assertThat(file).isEqualTo(readFile);
	}

	@Test
	public void IOWarFileTest() throws Exception {
		byte[] file = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.war"));
		fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war", file);
		byte[] readFile = fileService.getFileByFullPath("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war");

		assertThat(file).isEqualTo(readFile);
	}

}
