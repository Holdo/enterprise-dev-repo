package cz.muni.fi.pb138.webmvc;

import cz.muni.fi.pb138.api.FileService;
import cz.muni.fi.pb138.api.FileType;
import cz.muni.fi.pb138.dao.DatabaseDao;
import cz.muni.fi.pb138.service.processing.entity.PathVersionPair;
import org.basex.BaseXServer;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * Created by gasior on 15.05.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
public class FileServiceTests {

    @Autowired
    private FileService fileService;

    @BeforeClass
    public static void setUp() {
        BaseXServer.main(new String[]{});

    }

    @AfterClass
    public static void tearDown() throws IOException {
        BaseXServer.stop("localhost", 1984);
    }

    @Test
    public void saveFileTest() throws SAXException, DataFormatException, ParserConfigurationException, JAXBException, IOException {}
    @Test
    public void deleteFileTest(String fullPath){}
    @Test
    public void deleteFileTest(String fullPath, int version){}
    @Test
    public void getFileByFullPathTest(String fullPath){}
    @Test
    public void getFileByFullPathAndVersionTest(String fullPath, int version) {}
    @Test
    public void getAllFilesByFileTypeTest(FileType fileType, String namespace) {}
    @Test
    public void getFileVersionsTest(String fullPath){}

}
