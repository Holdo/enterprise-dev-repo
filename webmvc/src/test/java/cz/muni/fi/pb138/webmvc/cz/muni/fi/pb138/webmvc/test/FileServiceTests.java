package cz.muni.fi.pb138.webmvc.cz.muni.fi.pb138.webmvc.test;

import cz.muni.fi.pb138.api.FileService;
import cz.muni.fi.pb138.api.FileType;
import cz.muni.fi.pb138.dao.DatabaseDao;
import cz.muni.fi.pb138.service.processing.entity.PathVersionPair;
import cz.muni.fi.pb138.webmvc.AbstractIntegrationTest;
import org.apache.commons.io.IOUtils;
import org.basex.BaseXServer;
import org.basex.core.BaseXException;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * Created by gasior on 15.05.2016
 */
public class FileServiceTests extends AbstractIntegrationTest {
	private final String FILE_DATABASE_NAME = "artifacts";
	private final String META_DATABASE_NAME = "metadata";

	@Autowired
	private FileService fileService;

	@Autowired
	private DatabaseDao databaseDao;

	@BeforeClass
	public static void setUp() {
		BaseXServer.main(new String[]{});
	}

	@AfterClass
	public static void tearDown() throws IOException {
		BaseXServer.stop("localhost", 1984);
	}

	@Before
	public void createDatabase() throws IOException {
		databaseDao.createDatabase(FILE_DATABASE_NAME);
		databaseDao.createDatabase(META_DATABASE_NAME);
	}

	@After
	public void dropDatabase() throws IOException {
		databaseDao.dropDatabase(FILE_DATABASE_NAME);
		databaseDao.dropDatabase(META_DATABASE_NAME);
	}

	@Test
	public void IOFileTest() throws IOException, SAXException, DataFormatException, ParserConfigurationException, JAXBException {
		byte[] file = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.xsd"));
		byte[] file2 = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test2.xsd"));

		fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file);
		fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file2);

		byte[] readFile = fileService.getFileByFullPathAndVersion("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", 1);
		byte[] readFile2 = fileService.getFileByFullPathAndVersion("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", 2);
		byte[] readFileLast = fileService.getFileByFullPath("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");

		Assert.assertArrayEquals(file, readFile);
		Assert.assertArrayEquals(file2, readFile2);
		Assert.assertArrayEquals(file2, readFileLast);
	}

	@Test(expected = BaseXException.class)
	public void deleteFileTest() throws IOException, SAXException, DataFormatException, ParserConfigurationException, JAXBException {

		byte[] file = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.xsd"));
		byte[] file2 = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test2.xsd"));

		Assert.assertNotEquals(file, file2);
		fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file);
		fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file2);
		fileService.deleteFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");

		byte[] readFileLast = fileService.getFileByFullPath("/src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");
		Assert.assertArrayEquals(file, readFileLast);

		fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file2);
		fileService.deleteFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", 2);

		readFileLast = fileService.getFileByFullPath("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");
		Assert.assertArrayEquals(file, readFileLast);

		fileService.deleteFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");
		readFileLast = fileService.getFileByFullPath("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");

	}

	@Test
	public void getFileVersionsTest() throws IOException, SAXException, DataFormatException, ParserConfigurationException, JAXBException {

		byte[] file = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.xsd"));

		fileService.saveFile("/src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file);
		fileService.saveFile("/src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file);
		fileService.saveFile("/src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file);
		fileService.saveFile("/src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file);


		List<Integer> versions = fileService.getFileVersions("/src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");
		Assert.assertTrue(versions.contains(1) && versions.contains(3) && versions.contains(2) && versions.contains(4));

		fileService.deleteFile("/src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");
		fileService.deleteFile("/src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", 2);

		versions = fileService.getFileVersions("/src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");
		Assert.assertTrue(versions.contains(1) && versions.contains(3) && !versions.contains(2) && !versions.contains(4));

	}

	@Test
	public void getAllFilesByFileTypeTest() throws IOException, SAXException, DataFormatException, ParserConfigurationException, JAXBException {


		byte[] file = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.xsd"));
		byte[] file2 = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.wsdl"));
		byte[] file3 = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.war"));

		fileService.saveFile("/src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file);
		fileService.saveFile("/src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file);
		fileService.saveFile("/src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file);
		fileService.saveFile("/src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file);

		fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.wsdl", file2);
		fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.wsdl", file2);
		fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.wsdl", file2);
		fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.wsdl", file2);

		fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war", file3);
		fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war", file3);
		fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war", file3);
		fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war", file3);


		List<PathVersionPair> xsds = fileService.getAllFilesByFileType(FileType.XSD, "/src");
		List<PathVersionPair> wsdls = fileService.getAllFilesByFileType(FileType.WSDL, "src");
		List<PathVersionPair> wars = fileService.getAllFilesByFileType(FileType.WAR, "/");


		Assert.assertTrue(xsds.size() == 4);
		Assert.assertTrue(wars.size() == 4);
		Assert.assertTrue(wsdls.size() == 4);


		boolean xsdTest = true;
		boolean wsdlTest = true;
		boolean warTest = true;


		int i = 1;
		for (PathVersionPair p : xsds) {
			if (!p.getFullPath().equals("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd") | p.getVersion() != i) {
				xsdTest = false;
			}
			i++;
		}

		i = 1;
		for (PathVersionPair p : wars) {
			if (!p.getFullPath().equals("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war") | p.getVersion() != i) {
				warTest = false;
			}
			i++;
		}

		i = 1;
		for (PathVersionPair p : wsdls) {
			if (!p.getFullPath().equals("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.wsdl") | p.getVersion() != i) {
				wsdlTest = false;
			}
			i++;
		}

		Assert.assertTrue(xsdTest);
		Assert.assertTrue(warTest);
		Assert.assertTrue(wsdlTest);

	}


}
