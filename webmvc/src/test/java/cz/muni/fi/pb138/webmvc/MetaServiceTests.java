package cz.muni.fi.pb138.webmvc;

import cz.muni.fi.pb138.api.FileService;
import cz.muni.fi.pb138.api.FileType;
import cz.muni.fi.pb138.api.MetaFileType;
import cz.muni.fi.pb138.api.MetaParameterType;
import cz.muni.fi.pb138.dao.DatabaseDao;
import cz.muni.fi.pb138.service.processing.entity.MetaFilePathVersionTriplet;
import cz.muni.fi.pb138.service.processing.entity.PathVersionPair;
import org.basex.BaseXServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
public class MetaServiceTests {

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
   /* @Test
    public void  getFilesFullPathsByMetaParameterTest() {}
    @Test
    public void  getAllMetaParametersByMetaParameterTypeTest() {}
    @Test
    public void  getAllMetaFilesByMetaFileTypeTest(){}
    @Test
    public void  getMetaFileByFileFullPathTest() {}
    @Test
    public void  getMetaParametersByFileFullPathTest() {}
*/
}