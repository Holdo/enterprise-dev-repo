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
    private final String FILE_DATABASE_NAME = "artifacts";
    private final String META_DATABASE_NAME = "metadata";
    @Autowired
    private FileService fileService;
    @Autowired
    private DatabaseDao databaseDao;
    @BeforeClass
    public static void setUp() {
        BaseXServer.main(new String[]{});

    }

    @AfterClass
    public static void tearDown() throws IOException {
        BaseXServer.stop("localhost", 1984);
    }

    @Test
    public void IOFileTest() throws IOException, SAXException, DataFormatException, ParserConfigurationException, JAXBException {
        databaseDao.createDatabase(FILE_DATABASE_NAME);
        databaseDao.createDatabase(META_DATABASE_NAME);

        Path path = Paths.get("./src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");
        byte[] file = Files.readAllBytes(path);
        fileService.saveFile("/src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file);
        fileService.saveFile("/src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file);
        byte[] readFile = fileService.getFileByFullPath("/src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");
        byte[] readFileVersioned = fileService.getFileByFullPathAndVersion("/src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd",2);
        Assert.assertArrayEquals(file,readFile);
        Assert.assertArrayEquals(file,readFileVersioned);
        databaseDao.dropDatabase(FILE_DATABASE_NAME);
        databaseDao.dropDatabase(META_DATABASE_NAME);
    }
   /* @Test
    public void deleteFileTest(){}
    @Test
    public void deleteFileVersionedTest(){}
    @Test
    public void getFileByFullPathTest(){}
    @Test
    public void getFileByFullPathAndVersionTest() {}
    @Test
    public void getAllFilesByFileTypeTest() {}
    @Test
    public void getFileVersionsTest(){}*/

}
