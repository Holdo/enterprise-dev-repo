package cz.muni.fi.pb138.dao;

import cz.muni.fi.pb138.basex.BaseXContext;
import cz.muni.fi.pb138.entity.XQueryVariable;
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

	public String listDirectory(String database, String path) throws BaseXException {
		return new List(database, path).execute(dbCtx.getContext());
	}

	public String deleteFileOrDirectory(String fullPath) throws IOException {
		return new Delete(fullPath).execute(dbCtx.getContext());
	}
}
