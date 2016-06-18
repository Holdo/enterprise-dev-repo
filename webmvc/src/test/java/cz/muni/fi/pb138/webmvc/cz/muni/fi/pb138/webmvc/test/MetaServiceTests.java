package cz.muni.fi.pb138.webmvc.cz.muni.fi.pb138.webmvc.test;

import cz.muni.fi.pb138.entity.metadata.Items;
import cz.muni.fi.pb138.entity.metadata.VersionedFile;
import cz.muni.fi.pb138.enums.FileType;
import cz.muni.fi.pb138.enums.MetaFileType;
import cz.muni.fi.pb138.entity.metadata.MetaFilePathVersionTriplet;
import cz.muni.fi.pb138.enums.MetaParameterType;
import cz.muni.fi.pb138.webmvc.AbstractIntegrationTest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;

import static org.assertj.core.api.Assertions.*;

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



	@Test
	public void  getAllMetaFilesByMetaFileTypeTest() throws IOException {
		List<MetaFilePathVersionTriplet> output = metaService.getAllMetaFilesByMetaFileType(MetaFileType.WEBXML, "/");
		assertThat(output.size()).isEqualTo(2);
	}


	@Test
	public void getMetaFileByFileFullPathVersionedTest() throws IOException {
		byte[] reference1 = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("web.xml"));
		byte[] reference2 = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("web2.xml"));

		MetaFilePathVersionTriplet readWebxmlShouldBeVersion2 = metaService.getMetaFileByFileFullPath(MetaFileType.WEBXML, "src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war");
		MetaFilePathVersionTriplet readWebxmlShouldBeVersion2Too = metaService.getMetaFileByFileFullPathAndVersion(MetaFileType.WEBXML, "src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war", 2);
		MetaFilePathVersionTriplet readWebxmlShouldBeVersion1 = metaService.getMetaFileByFileFullPathAndVersion(MetaFileType.WEBXML, "src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war", 1);

		assertThat(readWebxmlShouldBeVersion1).isNotNull();
		assertThat(readWebxmlShouldBeVersion2).isNotNull();
		assertThat(readWebxmlShouldBeVersion2Too).isNotNull();

		FileUtils.writeByteArrayToFile(new File("./wbxmltest1.xml"),readWebxmlShouldBeVersion1.getFile());
		FileUtils.writeByteArrayToFile(new File("./wbxmltest2.xml"),readWebxmlShouldBeVersion2.getFile());
		FileUtils.writeByteArrayToFile(new File("./wbxmltest22.xml"),readWebxmlShouldBeVersion2Too.getFile());

		assertThat(reference1).isEqualTo(readWebxmlShouldBeVersion1.getFile());
		assertThat(reference2).isEqualTo(readWebxmlShouldBeVersion2.getFile());
		assertThat(reference2).isEqualTo(readWebxmlShouldBeVersion2Too.getFile());
	}

    @Test
	public void  getMetaParametersByFileFullPathVersionedTest() throws IOException, JAXBException {
		Items xsdMeta = metaService.getMetaParametersByFileFullPath("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");
		Items wsdlMeta = metaService.getMetaParametersByFileFullPath("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.wsdl");
		Items warMeta = metaService.getMetaParametersByFileFullPath("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war");
		assertThat(xsdMeta.getItem().size()).isGreaterThan(0);
		assertThat(wsdlMeta.getItem().size()).isGreaterThan(0);
		assertThat(warMeta.getItem().size()).isGreaterThan(0);
	}

	@Test
	public void  getFilesFullPathsByMetaParameterTest() throws IOException {

		List<VersionedFile> warsByListener = metaService.getFilesFullPathsByMetaParameter(FileType.WAR,MetaParameterType.LISTENER, "Listener1");
		List<VersionedFile> warsByFilter = metaService.getFilesFullPathsByMetaParameter(FileType.WAR,MetaParameterType.FILTER, "Filter1");

		List<VersionedFile> xsdsByAttribute = metaService.getFilesFullPathsByMetaParameter(FileType.WAR,MetaParameterType.ATTRIBUTE, "kategorie");
		List<VersionedFile> xsdsByElement = metaService.getFilesFullPathsByMetaParameter(FileType.WAR,MetaParameterType.ELEMENT, "divize");
		List<VersionedFile> xsdsByComplexType = metaService.getFilesFullPathsByMetaParameter(FileType.WAR,MetaParameterType.COMPLEXTYPE, "osobaType");
		List<VersionedFile> xsdsBySimpleType = metaService.getFilesFullPathsByMetaParameter(FileType.WAR,MetaParameterType.SIMPLETYPE, "dphType");

		List<VersionedFile> wsdlsByOperation = metaService.getFilesFullPathsByMetaParameter(FileType.WAR,MetaParameterType.OPERATION, "sayHello");
		List<VersionedFile> wsdlsByRequest = metaService.getFilesFullPathsByMetaParameter(FileType.WAR,MetaParameterType.REQUEST, "tsn:sayHelloRequest");
		List<VersionedFile> wsdlsByResponse = metaService.getFilesFullPathsByMetaParameter(FileType.WAR,MetaParameterType.RESPONSE, "tsn:sayHelloResponse");

		assertThat(warsByListener.size()).isGreaterThan(0);
		assertThat(warsByFilter.size()).isGreaterThan(0);

		assertThat(xsdsByAttribute.size()).isGreaterThan(0);
		assertThat(xsdsByElement.size()).isGreaterThan(0);
		assertThat(xsdsByComplexType.size()).isGreaterThan(0);
		assertThat(xsdsBySimpleType.size()).isGreaterThan(0);

		assertThat(wsdlsByOperation.size()).isGreaterThan(0);
		assertThat(wsdlsByRequest.size()).isGreaterThan(0);
		assertThat(wsdlsByResponse.size()).isGreaterThan(0);
	}


}