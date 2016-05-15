package cz.muni.fi.pb138.webmvc;

import cz.muni.fi.pb138.api.FileService;
import cz.muni.fi.pb138.api.FileType;
import cz.muni.fi.pb138.service.processing.FileProcessor;
import cz.muni.fi.pb138.service.processing.entity.PathVersionPair;
import cz.muni.fi.pb138.service.processing.entity.WarFile;
import cz.muni.fi.pb138.service.processing.entity.WsdlFile;
import cz.muni.fi.pb138.service.processing.entity.XsdFile;
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

        Path path = Paths.get("./src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.wsdl");
        byte[] file = Files.readAllBytes(path);
        WsdlFile parsedFile = (WsdlFile)fileProcessor.processWsdl("./src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.wsdl",file);

    }

    @Test
    public void xsdParserTest() throws Exception {
        Path path = Paths.get("./src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");
        byte[] file = Files.readAllBytes(path);
        XsdFile parsedFile = (XsdFile)fileProcessor.processXsd("./src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd",file);

    }

    @Test
    @Ignore
    public void warParserTest() throws Exception {
        Path path = Paths.get("./src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war");
        byte[] file = Files.readAllBytes(path);
        WarFile parsedFile = (WarFile)fileProcessor.processWar("./src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war",file);

    }
}
