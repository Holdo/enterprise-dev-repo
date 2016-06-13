package cz.muni.fi.pb138.webmvc;

import cz.muni.fi.pb138.dao.DatabaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Michal Holic on 13/06/2016
 */
@Component
public class DatabaseInitializer implements DisposableBean {

	private static final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);

	private final String FILE_DATABASE_NAME = "artifacts";
	private final String META_DATABASE_NAME = "metadata";

	@Autowired
	private DatabaseDao databaseDao;

	@PostConstruct
	public void initialize() throws Exception {
		log.info("Initializing databases...");
		databaseDao.checkDatabase(FILE_DATABASE_NAME);
		databaseDao.closeDatabase();
		databaseDao.checkDatabase(META_DATABASE_NAME);
		databaseDao.closeDatabase();
		this.destroy();
	}

	@Override
	public void destroy() throws Exception {
		log.info("Databases initialized, destroying {}.", getClass().getSimpleName());
	}
}
