package cz.muni.fi.pb138.webmvc.cz.muni.fi.pb138.webmvc.test;

import cz.muni.fi.pb138.enums.MetaFileType;
import cz.muni.fi.pb138.entity.war.WarFile;
import cz.muni.fi.pb138.entity.wsdl.WsdlFile;
import cz.muni.fi.pb138.entity.xsd.XsdFile;
import cz.muni.fi.pb138.webmvc.AbstractIntegrationTest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import java.io.File;

/**
 * Created by gasior on 15.05.2016
 */
public class FileParsersTests extends AbstractIntegrationTest {

	@Test
	public void wsdlParserTest() throws Exception {
		byte[] file = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.wsdl"));
		WsdlFile parsedFile = (WsdlFile) fileProcessor.processWsdl("src/test/resources/test.wsdl", file);
		FileUtils.writeByteArrayToFile(new File("src/test/resources/wsdlmeta.xml"), parsedFile.getMeta());
		assertThat(parsedFile.getOperations().size()).isGreaterThan(0);
		assertThat(parsedFile.getRequests().size()).isGreaterThan(0);
		assertThat(parsedFile.getResponses().size()).isGreaterThan(0);

	}

	@Test
	public void xsdParserTest() throws Exception {
		byte[] file = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.xsd"));
		XsdFile parsedFile = (XsdFile) fileProcessor.processXsd("src/test/resources/test.xsd", file);
		FileUtils.writeByteArrayToFile(new File("src/test/resources/xsdmeta.xml"), parsedFile.getMeta());
		assertThat(parsedFile.getAttributes().size()).isGreaterThan(0);
		assertThat(parsedFile.getComplexTypes().size()).isGreaterThan(0);
		assertThat(parsedFile.getElements().size()).isGreaterThan(0);
		assertThat(parsedFile.getSimpleTypes().size()).isGreaterThan(0);
	}

	@Test
	public void warParserTest() throws Exception {
		byte[] file = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.war"));
		WarFile parsedFile = (WarFile) fileProcessor.processWar("src/test/resources/test.war", file);
		FileUtils.writeByteArrayToFile(new File("src/test/resources/warmeta.xml"), parsedFile.getMeta());
		assertThat(parsedFile.getMetaFiles().get(MetaFileType.WEBXML)).isNotNull();
		assertThat(parsedFile.getFilterList().size()).isGreaterThan(0);
		assertThat(parsedFile.getListenerList().size()).isGreaterThan(0);
	}
}
