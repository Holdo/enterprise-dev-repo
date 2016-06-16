package cz.muni.fi.pb138.webmvc.cz.muni.fi.pb138.webmvc.test;

import cz.muni.fi.pb138.enums.FileType;
import cz.muni.fi.pb138.entity.metadata.PathVersionPair;
import cz.muni.fi.pb138.webmvc.AbstractIntegrationTest;
import org.apache.commons.io.IOUtils;
import org.basex.core.BaseXException;
import org.junit.*;
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

	private static byte[] testXSD1file;
	private static byte[] testWSDL1file;
	private static byte[] testWAR1file;
	
	private String testXSD1fullPath = "src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd";
	private String testWSDL1fullPath = "src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.wsdl";
	private String testWAR1fullPath = "src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war";

	@BeforeClass
	public static void loadBinaryFiles() throws IOException {
		testXSD1file = IOUtils.toByteArray(FileServiceTests.class.getClassLoader().getResourceAsStream("test.xsd"));
		testWSDL1file = IOUtils.toByteArray(FileServiceTests.class.getClassLoader().getResourceAsStream("test.wsdl"));
		testWAR1file = IOUtils.toByteArray(FileServiceTests.class.getClassLoader().getResourceAsStream("test.war"));
	}

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
	public void IOFileTest() throws IOException, SAXException, DataFormatException, ParserConfigurationException, JAXBException {
		byte[] file = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.xsd"));
		byte[] file2 = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test2.xsd"));

		fileService.saveFile(testXSD1fullPath, file);
		fileService.saveFile(testXSD1fullPath, file2);

		byte[] readFile = fileService.getFileByFullPathAndVersion(testXSD1fullPath, 1);
		byte[] readFile2 = fileService.getFileByFullPathAndVersion(testXSD1fullPath, 2);
		byte[] readFileLast = fileService.getFileByFullPath(testXSD1fullPath);

		Assert.assertArrayEquals(file, readFile);
		Assert.assertArrayEquals(file2, readFile2);
		Assert.assertArrayEquals(file2, readFileLast);
	}

	@Test(expected = BaseXException.class)
	public void deleteFileTest() throws IOException, SAXException, DataFormatException, ParserConfigurationException, JAXBException {
		byte[] file = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.xsd"));
		byte[] file2 = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test2.xsd"));

		Assert.assertNotEquals(file, file2);
		fileService.saveFile(testXSD1fullPath, file);
		fileService.saveFile(testXSD1fullPath, file2);
		fileService.deleteFile(testXSD1fullPath);

		byte[] readFileLast = fileService.getFileByFullPath(testXSD1fullPath);
		Assert.assertArrayEquals(file, readFileLast);

		fileService.saveFile(testXSD1fullPath, file2);
		fileService.deleteFile(testXSD1fullPath, 2);

		readFileLast = fileService.getFileByFullPath(testXSD1fullPath);
		Assert.assertArrayEquals(file, readFileLast);

		fileService.deleteFile(testXSD1fullPath);
		readFileLast = fileService.getFileByFullPath(testXSD1fullPath);

	}

	@Test
	public void listFileVersionsTest() throws IOException, SAXException, DataFormatException, ParserConfigurationException, JAXBException {
		byte[] file = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.xsd"));

		fileService.saveFile(testXSD1fullPath, file);
		fileService.saveFile(testXSD1fullPath, file);
		fileService.saveFile(testXSD1fullPath, file);
		fileService.saveFile(testXSD1fullPath, file);


		List<Integer> versions = fileService.listFileVersions(testXSD1fullPath);
		Assert.assertTrue(versions.contains(1) && versions.contains(3) && versions.contains(2) && versions.contains(4));

		fileService.deleteFile(testXSD1fullPath);
		fileService.deleteFile(testXSD1fullPath, 2);

		versions = fileService.listFileVersions(testXSD1fullPath);
		Assert.assertTrue(versions.contains(1) && versions.contains(3) && !versions.contains(2) && !versions.contains(4));
	}

	@Test
	public void getAllFilesTest() throws Exception {
		fileService.saveFile(testXSD1fullPath, testXSD1file);
		fileService.saveFile(testXSD1fullPath, testXSD1file);
		fileService.saveFile(testXSD1fullPath, testXSD1file);
		fileService.saveFile(testXSD1fullPath, testXSD1file);

		fileService.saveFile(testWSDL1fullPath, testWSDL1file);
		fileService.saveFile(testWSDL1fullPath, testWSDL1file);
		fileService.saveFile(testWSDL1fullPath, testWSDL1file);
		fileService.saveFile(testWSDL1fullPath, testWSDL1file);

		fileService.saveFile(testWAR1fullPath, testWAR1file);
		fileService.saveFile(testWAR1fullPath, testWAR1file);
		fileService.saveFile(testWAR1fullPath, testWAR1file);
		fileService.saveFile(testWAR1fullPath, testWAR1file);


		List<PathVersionPair> all = fileService.listAllFiles("/", true);
		List<PathVersionPair> allLatest = fileService.listAllFiles("/", false);
		List<PathVersionPair> xsds = fileService.listAllFilesByFileType(FileType.XSD, "/src");
		List<PathVersionPair> wsdls = fileService.listAllFilesByFileType(FileType.WSDL, "src");
		List<PathVersionPair> wars = fileService.listAllFilesByFileType(FileType.WAR, "/");

		Assert.assertTrue(all.size() == 12);
		Assert.assertTrue(allLatest.size() == 3);
		Assert.assertTrue(xsds.size() == 4);
		Assert.assertTrue(wars.size() == 4);
		Assert.assertTrue(wsdls.size() == 4);

		for (PathVersionPair p : allLatest) {
			Assert.assertTrue(p.getFullPath().startsWith("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.") && p.getVersion() == 4);
		}

		int i = 1;
		int j = 0;
		for (PathVersionPair p : all) {
			Assert.assertTrue(p.getFullPath().startsWith("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.") && p.getVersion() == i);
			j++;
			if (j == 3) {
				j = 0;
				i++;
			}
		}

		i = 1;
		for (PathVersionPair p : xsds) {
			Assert.assertTrue(p.getFullPath().equals(testXSD1fullPath) && p.getVersion() == i);
			i++;
		}

		i = 1;
		for (PathVersionPair p : wars) {
			Assert.assertTrue(p.getFullPath().equals("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war") && p.getVersion() == i);
			i++;
		}

		i = 1;
		for (PathVersionPair p : wsdls) {
			Assert.assertTrue(p.getFullPath().equals(testWSDL1fullPath) && p.getVersion() == i);
			i++;
		}
	}


}
