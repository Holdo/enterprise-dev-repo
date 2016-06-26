package cz.muni.fi.pb138.webmvc.cz.muni.fi.pb138.webmvc.test;

import cz.muni.fi.pb138.enums.FileType;
import cz.muni.fi.pb138.entity.metadata.VersionedFile;
import cz.muni.fi.pb138.webmvc.AbstractIntegrationTest;
import org.apache.commons.io.IOUtils;
import org.basex.core.BaseXException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by gasior on 15.05.2016
 */
public class FileServiceTests extends AbstractIntegrationTest {

	private static byte[] testXSD1file;
	private static byte[] testWSDL1file;
	private static byte[] testWAR1file;

	private String testNamespace = "src"+ "/"+"test"+"/"+"java"+"/"+"cz"+"/"+"muni"+"/"+"fi"+"/"+"pb138"+"/"+"webmvc"+"/"+"testfiles";
	private String testXSD1fullPath = testNamespace +"/"+ "test.xsd";
	private String testWSDL1fullPath = testNamespace + "/" + "test.wsdl";
	private String testWAR1fullPath = testNamespace + "/" + "test.war";

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
	public void IOFileTest() throws Exception {
		byte[] file = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.xsd"));
		byte[] file2 = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test2.xsd"));

		fileService.saveFile(testXSD1fullPath, file);
		fileService.saveFile(testXSD1fullPath, file2);

		byte[] readFile = fileService.getFileByFullPathAndVersion(testXSD1fullPath, 1);
		byte[] readFile2 = fileService.getFileByFullPathAndVersion(testXSD1fullPath, 2);
		byte[] readFileLast = fileService.getFileByFullPath(testXSD1fullPath);

		assertThat(file).isEqualTo(readFile);
		assertThat(file2).isEqualTo(readFile2);
		assertThat(file2).isEqualTo(readFileLast);
	}

	@Test
	public void deleteFileTest() throws Exception {
		byte[] file = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.xsd"));
		byte[] file2 = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test2.xsd"));

		assertThat(file).isNotEqualTo(file2);
		fileService.saveFile(testXSD1fullPath, file);
		fileService.saveFile(testXSD1fullPath, file2);
		fileService.deleteFile(testXSD1fullPath);

		byte[] readFileLast = fileService.getFileByFullPath(testXSD1fullPath);
		assertThat(file).isEqualTo(readFileLast);

		fileService.saveFile(testXSD1fullPath, file2);
		fileService.deleteFile(testXSD1fullPath, 2);

		readFileLast = fileService.getFileByFullPath(testXSD1fullPath);
		assertThat(file).isEqualTo(readFileLast);

		fileService.deleteFile(testXSD1fullPath);
		assertThatExceptionOfType(BaseXException.class).isThrownBy(() -> fileService.getFileByFullPath(testXSD1fullPath));

	}

	@Test
	public void listFileVersionsTest() throws Exception {
		byte[] file = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.xsd"));

		fileService.saveFile(testXSD1fullPath, file);
		fileService.saveFile(testXSD1fullPath, file);
		fileService.saveFile(testXSD1fullPath, file);
		fileService.saveFile(testXSD1fullPath, file);


		List<Integer> versions = fileService.listFileVersions(testXSD1fullPath);
		assertThat(versions).contains(1, 2, 3, 4);

		fileService.deleteFile(testXSD1fullPath);
		fileService.deleteFile(testXSD1fullPath, 2);

		versions = fileService.listFileVersions(testXSD1fullPath);
		assertThat(versions).contains(1, 3).doesNotContain(2, 4);
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


		List<VersionedFile> all = fileService.listAllFiles(testNamespace, true, false);
		List<VersionedFile> allLatest = fileService.listAllFiles(testNamespace, false, false);
		List<VersionedFile> xsds = fileService.listAllFilesByFileType(testNamespace, true, false, FileType.XSD);
		List<VersionedFile> wsdls = fileService.listAllFilesByFileType(testNamespace, true, false, FileType.WSDL);
		List<VersionedFile> wars = fileService.listAllFilesByFileType(testNamespace, true, false, FileType.WAR);

		assertThat(all.size()).isEqualTo(12);
		assertThat(allLatest.size()).isEqualTo(3);
		assertThat(xsds.size()).isEqualTo(4);
		assertThat(wars.size()).isEqualTo(4);
		assertThat(wsdls.size()).isEqualTo(4);

		for (VersionedFile p : allLatest) {
			assertThat(p.getFullPath()).startsWith(testNamespace + "/" + "test.");
			assertThat(p.getVersion()).isEqualTo(4);
			assertThat(p.isDirectory()).isFalse();
		}

		int i = 1;
		int j = 0;
		for (VersionedFile p : all) {
			assertThat(p.getFullPath()).startsWith(testNamespace +  "/" + "test.");
			assertThat(p.getVersion()).isEqualTo(i);
			assertThat(p.isDirectory()).isFalse();
			j++;
			if (j == 3) {
				j = 0;
				i++;
			}
		}

		i = 1;
		for (VersionedFile p : xsds) {
			assertThat(p.getFullPath()).isEqualTo(testXSD1fullPath);
			assertThat(p.getVersion()).isEqualTo(i);
			assertThat(p.isDirectory()).isFalse();
			i++;
		}

		i = 1;
		for (VersionedFile p : wars) {
			assertThat(p.getFullPath()).isEqualTo(testWAR1fullPath);
			assertThat(p.getVersion()).isEqualTo(i);
			assertThat(p.isDirectory()).isFalse();
			i++;
		}

		i = 1;
		for (VersionedFile p : wsdls) {
			assertThat(p.getFullPath()).isEqualTo(testWSDL1fullPath);
			assertThat(p.getVersion()).isEqualTo(i);
			assertThat(p.isDirectory()).isFalse();
			i++;
		}
	}


}
