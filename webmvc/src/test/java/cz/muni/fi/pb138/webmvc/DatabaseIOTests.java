package cz.muni.fi.pb138.webmvc;

import cz.muni.fi.pb138.api.FileService;
import cz.muni.fi.pb138.api.FileType;
import cz.muni.fi.pb138.service.processing.entity.PathVersionPair;
import org.basex.BaseXServer;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

/**
 * Created by gasior on 15.05.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
public class DatabaseIOTests {


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
        public void exampleTest() throws Exception {

        }
}
