package cz.muni.fi.pb138.dao;

import cz.muni.fi.pb138.basex.BaseXContext;
import cz.muni.fi.pb138.entity.XQueryVariable;
import org.basex.core.BaseXException;
import org.basex.core.cmd.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Michal Holič
 */
@Repository
public class DatabaseDaoImpl implements DatabaseDao {

	private static final Logger log = LoggerFactory.getLogger(DatabaseDao.class);

	@Value("${cz.muni.fi.pb138.xml-db-name}")
	private String XML_DATABASE_NAME;

	@Value("${cz.muni.fi.pb138.meta-db-name}")
	private String META_DATABASE_NAME;

	@Autowired
	private BaseXContext dbCtx;

	private String binaryDataLocation = null;
	private String binaryMetadataLocation = null;

	public String createDatabase(String name) throws IOException {
		return new CreateDB(name).execute(dbCtx.getContext());
	}

	public String checkDatabase(String name) throws IOException {
		return new Check(name).execute(dbCtx.getContext());
	}

	public String openDatabase(String name) throws IOException {
		return new Open(name).execute(dbCtx.getContext());
	}

	public String closeDatabase() throws IOException {
		return new Close().execute(dbCtx.getContext());
	}

	public String dropDatabase(String name) throws IOException {
		return new DropDB(name).execute(dbCtx.getContext());
	}

	public String getBinaryDataFileSystemRoot() {
		return binaryDataLocation;
	}

	public String getBinaryMetadataFileSystemRoot() {
		return binaryMetadataLocation;
	}

	public String runXQuery(String xQuery) throws BaseXException {
		if (log.isDebugEnabled()) {
			if (xQuery.contains("\n")) log.debug("Executing XQuery:\n{}", xQuery);
			else log.debug("Executing: {}", xQuery);
		}
		return new XQuery(xQuery).execute(dbCtx.getContext());
	}

	public String runXQuery(String xQuery, XQueryVariable... variables) throws BaseXException {
		XQuery xq = new XQuery(xQuery);
		for (XQueryVariable variable : variables) {
			xq.bind(variable.getName(), variable.getValue(), variable.getType().toString());
		}
		if (log.isDebugEnabled()) {
			if (xQuery.contains("\n")) log.debug("Executing XQuery:\n{}", xQuery);
			else log.debug("Executing: {}", xQuery);
		}
		for (XQueryVariable variable : variables) {
			log.debug(variable.toString());
		}
		return xq.execute(dbCtx.getContext());
	}

	public String listDatabases() throws BaseXException {
		return new List().execute(dbCtx.getContext());
	}

	public String listDirectory(String database, String path) throws BaseXException {
		return new List(database, path).execute(dbCtx.getContext());
	}

	public String deleteFileOrDirectory(String fullPath) throws IOException {
		return new Delete(fullPath).execute(dbCtx.getContext());
	}

	@PostConstruct
	public void postConstruct() {
		log.debug("Locating BaseX data path");
		try (BufferedReader br = new BufferedReader(new FileReader(runXQuery("Q{org.basex.util.Prop}USERHOME()") + ".basex"))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("DBPATH")) {
					line = line.substring(line.lastIndexOf(" ") + 1);
					break;
				}
			}
			if (line == null) log.error("BaseX data path not found");
			else {
				binaryDataLocation = line + File.separator + XML_DATABASE_NAME + File.separator + "raw" + File.separator;
				binaryMetadataLocation = line + File.separator + META_DATABASE_NAME + File.separator + "raw" + File.separator;
			}
		} catch (IOException e) {
			log.error("Exception during locating BaseX data path", e);
		}
	}
}
