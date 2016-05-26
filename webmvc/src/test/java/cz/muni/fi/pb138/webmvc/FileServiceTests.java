package cz.muni.fi.pb138.webmvc;

import cz.muni.fi.pb138.api.FileService;
import cz.muni.fi.pb138.api.FileType;
import cz.muni.fi.pb138.dao.DatabaseDao;
import cz.muni.fi.pb138.service.processing.entity.PathVersionPair;
import org.basex.BaseXServer;
import org.basex.core.BaseXException;
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
        Path path2 = Paths.get("./src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test2.xsd");

        byte[] file = Files.readAllBytes(path);
        byte[] file2 = Files.readAllBytes(path2);

        fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file);
        fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file2);

        byte[] readFile = fileService.getFileByFullPathAndVersion("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd",1);
        byte[] readFile2 = fileService.getFileByFullPathAndVersion("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd",2);
        byte[] readFileLast = fileService.getFileByFullPath("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");

        Assert.assertArrayEquals(file,readFile);
        Assert.assertArrayEquals(file2,readFile2);
        Assert.assertArrayEquals(file2,readFileLast);

        databaseDao.dropDatabase(FILE_DATABASE_NAME);
        databaseDao.dropDatabase(META_DATABASE_NAME);
    }
    @Test(expected=BaseXException.class)
    public void deleteFileTest() throws IOException, SAXException, DataFormatException, ParserConfigurationException, JAXBException {

        databaseDao.createDatabase(FILE_DATABASE_NAME);
        databaseDao.createDatabase(META_DATABASE_NAME);

        Path path = Paths.get("./src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");
        Path path2 = Paths.get("./src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test2.xsd");

        byte[] file = Files.readAllBytes(path);
        byte[] file2 = Files.readAllBytes(path2);
        Assert.assertNotEquals(file,file2);
        fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file);
        fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file2);
        fileService.deleteFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");

        byte[] readFileLast = fileService.getFileByFullPath("/src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");
        Assert.assertArrayEquals(file,readFileLast);

        fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file2);
        fileService.deleteFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd",2);

        readFileLast = fileService.getFileByFullPath("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");
        Assert.assertArrayEquals(file,readFileLast);

        fileService.deleteFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");
        readFileLast = fileService.getFileByFullPath("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");


        databaseDao.dropDatabase(FILE_DATABASE_NAME);
        databaseDao.dropDatabase(META_DATABASE_NAME);
    }
    @Test
    public void getFileVersionsTest() throws IOException, SAXException, DataFormatException, ParserConfigurationException, JAXBException {

        databaseDao.createDatabase(FILE_DATABASE_NAME);
        databaseDao.createDatabase(META_DATABASE_NAME);

        Path path = Paths.get("./src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");
        byte[] file = Files.readAllBytes(path);

        fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file);
        fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file);
        fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file);
        fileService.saveFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file);


        List<Integer> versions = fileService.getFileVersions("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");
        Assert.assertTrue(versions.contains(1) && versions.contains(3) && versions.contains(2) && versions.contains(4));

        fileService.deleteFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");
        fileService.deleteFile("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd",2);

        versions = fileService.getFileVersions("src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");
        Assert.assertTrue(versions.contains(1) && versions.contains(3) && !versions.contains(2) && !versions.contains(4));

        databaseDao.dropDatabase(FILE_DATABASE_NAME);
        databaseDao.dropDatabase(META_DATABASE_NAME);
    }
    /*
    @Test
    public void getAllFilesByFileTypeTest() {

    }*/


}
