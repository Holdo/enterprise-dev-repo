package cz.muni.fi.pb138.dao;

import cz.muni.fi.pb138.basex.BaseXContext;
import org.basex.core.BaseXException;
import org.basex.core.cmd.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;

/**
 * Created by Michal Holic on 10/05/2016
 */
@Repository
public class DatabaseDaoImpl implements DatabaseDao {

	@Autowired
	private BaseXContext dbCtx;

	public String openDatabase(String name) throws IOException {
		return new Open(name).execute(dbCtx.getContext());
	}

	public String closeDatabase() throws IOException {
		return new Close().execute(dbCtx.getContext());
	}

	public String dropDatabase(String name) throws IOException {
		return new DropDB(name).execute(dbCtx.getContext());
	}

	public String deleteFileOrDirectory(String fullPath) throws IOException {
		return new Delete(fullPath).execute(dbCtx.getContext());
	}

	public String runXQuery(String xQuery) throws BaseXException {
		return new XQuery(xQuery).execute(dbCtx.getContext());
	}
}