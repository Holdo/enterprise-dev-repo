package cz.muni.fi.pb138.dao;

import cz.muni.fi.pb138.basex.BaseXContext;
import cz.muni.fi.pb138.entity.XQueryVariable;
import org.basex.core.BaseXException;
import org.basex.core.cmd.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Michal Holic on 10/05/2016
 */
@Repository
public class DatabaseDaoImpl implements DatabaseDao {

	private static final Logger log = LoggerFactory.getLogger(DatabaseDao.class);

	@Autowired
	private BaseXContext dbCtx;

	private String dataLocation = null;

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

	public String getDatabaseFileSystemLocation() {
		return dataLocation;
	}

	public String runXQuery(String xQuery) throws BaseXException {
		return new XQuery(xQuery).execute(dbCtx.getContext());
	}

	public String runXQuery(String xQuery, XQueryVariable... variables) throws BaseXException {
		XQuery xq = new XQuery(xQuery);
		for (XQueryVariable variable : variables) {
			xq.bind(variable.getName(), variable.getValue(), variable.getType().toString());
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
					dataLocation = line.substring(line.lastIndexOf(" ") + 1);
					break;
				}
			}
			if (dataLocation == null) log.error("BaseX data path not found");
		} catch (IOException e) {
			log.error("Exception during locating BaseX data path", e);
		}
	}
}
