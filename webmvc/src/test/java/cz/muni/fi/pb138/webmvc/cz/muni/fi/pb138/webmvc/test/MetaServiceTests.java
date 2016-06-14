package cz.muni.fi.pb138.webmvc.cz.muni.fi.pb138.webmvc.test;

import cz.muni.fi.pb138.enums.MetaFileType;
import cz.muni.fi.pb138.service.processing.entity.MetaFilePathVersionTriplet;
import cz.muni.fi.pb138.webmvc.AbstractIntegrationTest;
import org.apache.commons.io.IOUtils;
import org.junit.*;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.DataFormatException;

public class MetaServiceTests extends AbstractIntegrationTest {

	@Before
	public void createDatabase() throws IOException, SAXException, DataFormatException, ParserConfigurationException, JAXBException {
		databaseDao.createDatabase(XML_DATABASE_NAME);
		databaseDao.createDatabase(META_DATABASE_NAME);

		byte[] fileXsd1 = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.xsd"));
		byte[] fileXsd2 = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test2.xsd"));

		byte[] fileWsdl1 = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.wsdl"));
		byte[] fileWsdl2 = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test2.wsdl"));

		byte[] fileWar1 = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.war"));
		byte[] fileWar2 = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test2.war"));

		fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", fileXsd1);
		fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", fileXsd2);

		fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.wsdl", fileWsdl1);
		fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.wsdl", fileWsdl2);

		fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war", fileWar1);
		fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war", fileWar2);
	}

	@After
	public void dropDatabase() throws IOException {
		databaseDao.dropDatabase(XML_DATABASE_NAME);
		databaseDao.dropDatabase(META_DATABASE_NAME);
	}

	/*@Test
	public void  getFilesFullPathsByMetaParameterTest()  {

	}

	@Test
	public void  getAllMetaParametersByMetaParameterTypeTest() {}

	@Test
	public void  getAllMetaFilesByMetaFileTypeTest(){}*/

	@Test
	@Ignore //TODO fixme
	public void getMetaFileByFileFullPathVersionedTest() throws IOException {
		byte[] reference = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("web.xml"));

		MetaFilePathVersionTriplet readWebxmlShouldBeVersion2 = metaService.getMetaFileByFileFullPath(MetaFileType.WEBXML, "src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war");
		MetaFilePathVersionTriplet readWebxmlShouldBeVersion2Too = metaService.getMetaFileByFileFullPathAndVersion(MetaFileType.WEBXML, "src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war", 2);
		MetaFilePathVersionTriplet readWebxmlShouldBeVersion1 = metaService.getMetaFileByFileFullPathAndVersion(MetaFileType.WEBXML, "src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war", 1);

		Assert.assertNotNull(readWebxmlShouldBeVersion1);
		Assert.assertNotNull(readWebxmlShouldBeVersion2);
		Assert.assertNotNull(readWebxmlShouldBeVersion2Too);

		Assert.assertTrue(Arrays.equals(reference, readWebxmlShouldBeVersion1.getFile()));
		Assert.assertTrue(Arrays.equals(reference, readWebxmlShouldBeVersion2.getFile()));
		Assert.assertTrue(Arrays.equals(reference, readWebxmlShouldBeVersion2Too.getFile()));
	}

   /* @Test
	public void  getMetaParametersByFileFullPathVersionedTest() {}*/

}