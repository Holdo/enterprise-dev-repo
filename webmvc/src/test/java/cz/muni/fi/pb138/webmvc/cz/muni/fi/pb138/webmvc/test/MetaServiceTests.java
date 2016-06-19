package cz.muni.fi.pb138.webmvc.cz.muni.fi.pb138.webmvc.test;

import cz.muni.fi.pb138.entity.metadata.Items;
import cz.muni.fi.pb138.entity.metadata.VersionedFile;
import cz.muni.fi.pb138.enums.FileType;
import cz.muni.fi.pb138.enums.MetaFileType;
import cz.muni.fi.pb138.entity.metadata.MetaFilePathVersionTriplet;
import cz.muni.fi.pb138.enums.MetaParameterType;
import cz.muni.fi.pb138.webmvc.AbstractIntegrationTest;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
	public void getAllMetaFilesByMetaFileTypeTest() throws IOException {
		List<MetaFilePathVersionTriplet> output = metaService.getAllMetaFilesByMetaFileType(MetaFileType.WEBXML, "/");
		assertThat(output.size()).isEqualTo(2);
	}


	@Test
	public void getMetaFileByFileFullPathVersionedTest() throws Exception {
		byte[] reference1 = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("web.xml"));
		byte[] reference2 = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("web2.xml"));

		MetaFilePathVersionTriplet readWebxmlShouldBeVersion2 = metaService.getMetaFileByFileFullPath(MetaFileType.WEBXML, "src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war");
		MetaFilePathVersionTriplet readWebxmlShouldBeVersion2Too = metaService.getMetaFileByFileFullPathAndVersion(MetaFileType.WEBXML, "src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war", 2);
		MetaFilePathVersionTriplet readWebxmlShouldBeVersion1 = metaService.getMetaFileByFileFullPathAndVersion(MetaFileType.WEBXML, "src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war", 1);

		assertThat(readWebxmlShouldBeVersion1).isNotNull();
		assertThat(readWebxmlShouldBeVersion2).isNotNull();
		assertThat(readWebxmlShouldBeVersion2Too).isNotNull();

		Charset utf8 = StandardCharsets.UTF_8;
		assertThat(new String(reference1, utf8)).isEqualToIgnoringWhitespace(new String(readWebxmlShouldBeVersion1.getFile(), utf8));
		assertThat(new String(reference2, utf8)).isEqualToIgnoringWhitespace(new String(readWebxmlShouldBeVersion2.getFile(), utf8));
		assertThat(new String(reference2, utf8)).isEqualToIgnoringWhitespace(new String(readWebxmlShouldBeVersion2Too.getFile(), utf8));
	}

	@Test
	public void getMetaParametersByFileFullPathVersionedTest() throws IOException, JAXBException {
		List<Items> xsdMeta = metaService.getMetaParametersByFileFullPath("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");
		List<Items> xsdMetaVersioned = metaService.getMetaParametersByFileFullPathAndVersion("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", 1);

		List<Items> wsdlMeta = metaService.getMetaParametersByFileFullPath("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.wsdl");
		List<Items> wsdlMetaVersioned = metaService.getMetaParametersByFileFullPathAndVersion("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.wsdl", 1);

		List<Items> warMeta = metaService.getMetaParametersByFileFullPath("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war");
		List<Items> warMetaVersioned = metaService.getMetaParametersByFileFullPathAndVersion("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war", 1);

		for (Items i : xsdMeta) {
			assertThat(i.getItem().length).isGreaterThan(0);
		}
		for (Items i : xsdMetaVersioned) {
			assertThat(i.getItem().length).isGreaterThan(0);
		}

		for (Items i : wsdlMeta) {
			assertThat(i.getItem().length).isGreaterThan(0);
		}
		for (Items i : wsdlMetaVersioned) {
			assertThat(i.getItem().length).isGreaterThan(0);
		}

		for (Items i : warMeta) {
			assertThat(i.getItem().length).isGreaterThan(0);
		}
		for (Items i : warMetaVersioned) {
			assertThat(i.getItem().length).isGreaterThan(0);
		}
	}

	@Test
	public void getAllMetaParametersByTypeTest() throws IOException, JAXBException {
		List<Items> attributes = metaService.getAllMetaParametersByMetaParameterType(MetaParameterType.ATTRIBUTE);
		List<Items> elements = metaService.getAllMetaParametersByMetaParameterType(MetaParameterType.ELEMENT);
		List<Items> complexTypes = metaService.getAllMetaParametersByMetaParameterType(MetaParameterType.COMPLEXTYPE);
		List<Items> simpleTypes = metaService.getAllMetaParametersByMetaParameterType(MetaParameterType.SIMPLETYPE);

		List<Items> operations = metaService.getAllMetaParametersByMetaParameterType(MetaParameterType.OPERATION);
		List<Items> requests = metaService.getAllMetaParametersByMetaParameterType(MetaParameterType.REQUEST);
		List<Items> responses = metaService.getAllMetaParametersByMetaParameterType(MetaParameterType.RESPONSE);

		List<Items> filters = metaService.getAllMetaParametersByMetaParameterType(MetaParameterType.FILTER);
		List<Items> listeners = metaService.getAllMetaParametersByMetaParameterType(MetaParameterType.LISTENER);

		for (Items i : attributes) {
			assertThat(i.getItem().length).isGreaterThan(0);
		}
		for (Items i : elements) {
			assertThat(i.getItem().length).isGreaterThan(0);
		}
		for (Items i : complexTypes) {
			assertThat(i.getItem().length).isGreaterThan(0);
		}
		for (Items i : simpleTypes) {
			assertThat(i.getItem().length).isGreaterThan(0);
		}

		for (Items i : operations) {
			assertThat(i.getItem().length).isGreaterThan(0);
		}
		for (Items i : requests) {
			assertThat(i.getItem().length).isGreaterThan(0);
		}
		for (Items i : responses) {
			assertThat(i.getItem().length).isGreaterThan(0);
		}

		for (Items i : filters) {
			assertThat(i.getItem().length).isGreaterThan(0);
		}
		for (Items i : listeners) {
			assertThat(i.getItem().length).isGreaterThan(0);
		}

	}

	@Test
	public void getFilesFullPathsByMetaParameterTest() throws IOException, JAXBException {
		List<VersionedFile> warsByListener = metaService.getFilesFullPathsByMetaParameter(FileType.WAR, MetaParameterType.LISTENER, "Listener1");
		List<VersionedFile> warsByFilter = metaService.getFilesFullPathsByMetaParameter(FileType.WAR, MetaParameterType.FILTER, "Filter1");

		List<VersionedFile> xsdsByAttribute = metaService.getFilesFullPathsByMetaParameter(FileType.XSD, MetaParameterType.ATTRIBUTE, "kategorie");
		List<VersionedFile> xsdsByElement = metaService.getFilesFullPathsByMetaParameter(FileType.XSD, MetaParameterType.ELEMENT, "divize");
		List<VersionedFile> xsdsByComplexType = metaService.getFilesFullPathsByMetaParameter(FileType.XSD, MetaParameterType.COMPLEXTYPE, "osobaType");
		List<VersionedFile> xsdsBySimpleType = metaService.getFilesFullPathsByMetaParameter(FileType.XSD, MetaParameterType.SIMPLETYPE, "dphType");

		List<VersionedFile> wsdlsByOperation = metaService.getFilesFullPathsByMetaParameter(FileType.WSDL, MetaParameterType.OPERATION, "sayHello");
		List<VersionedFile> wsdlsByRequest = metaService.getFilesFullPathsByMetaParameter(FileType.WSDL, MetaParameterType.REQUEST, "tns:SayHelloRequest");
		List<VersionedFile> wsdlsByResponse = metaService.getFilesFullPathsByMetaParameter(FileType.WSDL, MetaParameterType.RESPONSE, "tns:SayHelloResponse");

		assertThat(warsByListener.size()).isGreaterThan(0);
		assertThat(warsByFilter.size()).isGreaterThan(0);

		assertThat(xsdsByAttribute.size()).isGreaterThan(1);
		assertThat(xsdsByElement.size()).isGreaterThan(1);
		assertThat(xsdsByComplexType.size()).isGreaterThan(1);
		assertThat(xsdsBySimpleType.size()).isGreaterThan(1);

		assertThat(wsdlsByOperation.size()).isGreaterThan(1);
		assertThat(wsdlsByRequest.size()).isGreaterThan(1);
		assertThat(wsdlsByResponse.size()).isGreaterThan(1);
	}


}