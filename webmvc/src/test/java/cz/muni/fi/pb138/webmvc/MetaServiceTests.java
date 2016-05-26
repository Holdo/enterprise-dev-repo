package cz.muni.fi.pb138.webmvc;

import cz.muni.fi.pb138.api.FileService;
import cz.muni.fi.pb138.api.FileType;
import cz.muni.fi.pb138.api.MetaFileType;
import cz.muni.fi.pb138.api.MetaParameterType;
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
    public void  getFilesFullPathsByMetaParameterTest(FileType fileType, MetaParameterType parameterType, String namespace, String parameterName) {}
    @Test
    public void  getAllMetaParametersByMetaParameterTypeTest(MetaParameterType parameterType, String namespace) {}
    @Test
    public void  getAllMetaFilesByMetaFileTypeTest(MetaFileType metaFileType, String namespace){}
    @Test
    public void  getMetaFileByFileFullPathTest(MetaFileType metaFileType, String fullPath, int version) {}
    @Test
    public void  getMetaParametersByFileFullPathTest(MetaParameterType parameterType, String fullPath, String version) {}

}