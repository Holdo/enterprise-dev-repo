package cz.muni.fi.pb138.webmvc.cz.muni.fi.pb138.webmvc.test;

import cz.muni.fi.pb138.enumtype.MetaFileType;
import cz.muni.fi.pb138.service.processing.FileProcessor;
import cz.muni.fi.pb138.service.processing.entity.WarFile;
import cz.muni.fi.pb138.service.processing.entity.WsdlFile;
import cz.muni.fi.pb138.service.processing.entity.XsdFile;
import cz.muni.fi.pb138.webmvc.AbstractIntegrationTest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.basex.BaseXServer;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

/**
 * Created by gasior on 15.05.2016
 */
public class FileParsersTests extends AbstractIntegrationTest {


	@Autowired
	private FileProcessor fileProcessor;

	@BeforeClass
	public static void setUp() {
		BaseXServer.main(new String[]{});

	}

	@AfterClass
	public static void tearDown() throws IOException {
		BaseXServer.stop("localhost", 1984);
	}

	@Test
	public void wsdlParserTest() throws Exception {

		byte[] file = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.wsdl"));
		WsdlFile parsedFile = (WsdlFile) fileProcessor.processWsdl("src/test/resources/test.wsdl", file);
		FileUtils.writeByteArrayToFile(new File("src/test/resources/wsdlmeta.xml"), parsedFile.getMeta());
		Assert.assertTrue(0 < parsedFile.getOperations().size());
		Assert.assertTrue(0 < parsedFile.getRequests().size());
		Assert.assertTrue(0 < parsedFile.getResponses().size());

	}

	@Test
	public void xsdParserTest() throws Exception {


		byte[] file = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.xsd"));
		XsdFile parsedFile = (XsdFile) fileProcessor.processXsd("src/test/resources/test.xsd", file);
		FileUtils.writeByteArrayToFile(new File("src/test/resources/xsdmeta.xml"), parsedFile.getMeta());
		Assert.assertTrue(0 < parsedFile.getAttributes().size());
		Assert.assertTrue(0 < parsedFile.getComplexTypes().size());
		Assert.assertTrue(0 < parsedFile.getElements().size());
		Assert.assertTrue(0 < parsedFile.getSimpleTypes().size());
	}

	@Test
	public void warParserTest() throws Exception {

		byte[] file = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.war"));
		WarFile parsedFile = (WarFile) fileProcessor.processWar("src/test/resources/test.war", file);
		FileUtils.writeByteArrayToFile(new File("src/test/resources/warmeta.xml"), parsedFile.getMeta());
		Assert.assertNotNull(parsedFile.getMetaFiles().get(MetaFileType.WEBXML));
		Assert.assertTrue(0 < parsedFile.getFilterList().size());
		Assert.assertTrue(0 < parsedFile.getListenerList().size());
	}
}
