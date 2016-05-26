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

import java.io.IOException;
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
public class DatabaseIOTests {

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
        public void IOXsdFileTest() throws Exception {

                databaseDao.createDatabase(FILE_DATABASE_NAME);
                databaseDao.createDatabase(META_DATABASE_NAME);

                Path path = Paths.get("./src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");
                byte[] file = Files.readAllBytes(path);
                fileService.saveFile("/src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd", file);
                byte[] readFile = fileService.getFileByFullPath("/src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.xsd");


                databaseDao.dropDatabase(FILE_DATABASE_NAME);
                databaseDao.dropDatabase(META_DATABASE_NAME);

                Assert.assertArrayEquals(file,readFile);


        }
        @Test
        public void IOWsdlFileTest() throws Exception {

                databaseDao.createDatabase(FILE_DATABASE_NAME);
                databaseDao.createDatabase(META_DATABASE_NAME);

                Path path = Paths.get("./src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.wsdl");
                byte[] file = Files.readAllBytes(path);
                fileService.saveFile("/src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.wsdl", file);
                byte[] readFile = fileService.getFileByFullPath("/src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.wsdl");


                databaseDao.dropDatabase(FILE_DATABASE_NAME);
                databaseDao.dropDatabase(META_DATABASE_NAME);

                Assert.assertArrayEquals(file,readFile);


        }
        @Test
        public void IOWarFileTest() throws Exception {

                databaseDao.createDatabase(FILE_DATABASE_NAME);
                databaseDao.createDatabase(META_DATABASE_NAME);

                Path path = Paths.get("./src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war");
                byte[] file = Files.readAllBytes(path);
                fileService.saveFile("/src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war", file);
                byte[] readFile = fileService.getFileByFullPath("/src/test/java/cz/muni/fi/pb138/webmvc/testfiles/test.war");


                databaseDao.dropDatabase(FILE_DATABASE_NAME);
                databaseDao.dropDatabase(META_DATABASE_NAME);

                Assert.assertArrayEquals(file,readFile);


        }

}
