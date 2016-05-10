package cz.muni.fi.pb138.dao;

import cz.muni.fi.pb138.basex.BaseXClient;
import cz.muni.fi.pb138.basex.BaseXSessionSingleton;

import java.io.IOException;

/**
 * Created by Michal Holic on 10/05/2016
 */
public class BaseXDaoImpl implements BaseXDao {

	public void createDatabase(String name) throws IOException {
		BaseXClient session = BaseXSessionSingleton.getSession();
		session.execute("create db " + name);
	}

	public void dropDatabase(String name) throws IOException {
		BaseXClient session = BaseXSessionSingleton.getSession();
		session.execute("drop db " + name);
	}
}
