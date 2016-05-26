package cz.muni.fi.pb138.webmvc;

import cz.muni.fi.pb138.api.FileService;
import cz.muni.fi.pb138.api.FileType;
import cz.muni.fi.pb138.api.MetaFileType;
import cz.muni.fi.pb138.service.processing.FileProcessor;
import cz.muni.fi.pb138.service.processing.entity.PathVersionPair;
import cz.muni.fi.pb138.service.processing.entity.WarFile;
import cz.muni.fi.pb138.service.processing.entity.WsdlFile;
import cz.muni.fi.pb138.service.processing.entity.XsdFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.basex.BaseXServer;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.AssertionErrors;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by gasior on 15.05.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
public class FileParsersTests {


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
        WsdlFile parsedFile = (WsdlFile)fileProcessor.processWsdl("src/test/resources/test.wsdl",file);
        FileUtils.writeByteArrayToFile(new File("src/test/resources/wsdlmeta.xml"),parsedFile.getMeta());
        Assert.assertTrue( 0 < parsedFile.getOperations().size());
        Assert.assertTrue( 0 < parsedFile.getRequests().size());
        Assert.assertTrue( 0 < parsedFile.getResponses().size());

    }

    @Test
    public void xsdParserTest() throws Exception {


        byte[] file = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.xsd"));
        XsdFile parsedFile = (XsdFile)fileProcessor.processXsd("src/test/resources/test.xsd",file);
        FileUtils.writeByteArrayToFile(new File("src/test/resources/xsdmeta.xml"),parsedFile.getMeta());
        Assert.assertTrue( 0 < parsedFile.getAttributes().size());
        Assert.assertTrue( 0 < parsedFile.getComplexTypes().size());
        Assert.assertTrue( 0 < parsedFile.getElements().size());
        Assert.assertTrue( 0 < parsedFile.getSimpleTypes().size());
    }

    @Test
    public void warParserTest() throws Exception {

        byte[] file = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("test.war"));
        WarFile parsedFile = (WarFile)fileProcessor.processWar("src/test/resources/test.war",file);
        FileUtils.writeByteArrayToFile(new File("src/test/resources/warmeta.xml"),parsedFile.getMeta());
        Assert.assertNotNull(parsedFile.getMetaFiles().get(MetaFileType.WEBXML));
        Assert.assertTrue( 0 < parsedFile.getFilterList().size());
        Assert.assertTrue( 0 < parsedFile.getListenerList().size());
    }
}
