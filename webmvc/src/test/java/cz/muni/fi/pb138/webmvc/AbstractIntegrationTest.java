package cz.muni.fi.pb138.webmvc;

import cz.muni.fi.pb138.api.FileService;
import cz.muni.fi.pb138.api.MetaService;
import cz.muni.fi.pb138.dao.DatabaseDao;
import cz.muni.fi.pb138.service.processing.FileProcessor;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("/test.properties")
public abstract class AbstractIntegrationTest extends TestWatcher {
	//Serves as an integration tests initializer for all the other tests

	@Value("${cz.muni.fi.pb138.xml-db-name}")
	protected String XML_DATABASE_NAME;

	@Value("${cz.muni.fi.pb138.meta-db-name}")
	protected String META_DATABASE_NAME;

	@Autowired
	protected FileService fileService;

	@Autowired
	protected MetaService metaService;

	@Autowired
	protected DatabaseDao databaseDao;

	@Autowired
	protected FileProcessor fileProcessor;

	@Override
	protected void failed(Throwable e, Description description) {
		try {
			databaseDao.dropDatabase(XML_DATABASE_NAME);
		} catch (IOException e1) {
			//Ignore
		}
		try {
			databaseDao.dropDatabase(META_DATABASE_NAME);
		} catch (IOException e1) {
			//Ignore
		}
	}
}
