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


		List<PathVersionPair> all = fileService.getAllFiles("/");
		List<PathVersionPair> xsds = fileService.getAllFilesByFileType(FileType.XSD, "/src");
		List<PathVersionPair> wsdls = fileService.getAllFilesByFileType(FileType.WSDL, "src");
		List<PathVersionPair> wars = fileService.getAllFilesByFileType(FileType.WAR, "/");

		Assert.assertTrue(all.size() == 12);
		Assert.assertTrue(xsds.size() == 4);
		Assert.assertTrue(wars.size() == 4);
		Assert.assertTrue(wsdls.size() == 4);

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
			Assert.assertTrue(p.getFullPath().equals("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd") && p.getVersion() == i);
			i++;
		}

		i = 1;
		for (PathVersionPair p : wars) {
			Assert.assertTrue(p.getFullPath().equals("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war") && p.getVersion() == i);
			i++;
		}

		i = 1;
		for (PathVersionPair p : wsdls) {
			Assert.assertTrue(p.getFullPath().equals("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.wsdl") && p.getVersion() == i);
			i++;
		}
	}


}
