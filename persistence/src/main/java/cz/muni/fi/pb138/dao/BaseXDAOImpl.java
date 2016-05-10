package cz.muni.fi.pb138.dao;

import cz.muni.fi.pb138.basex.BaseXClient;

import java.io.IOException;

/**
 * Created by Michal Holic on 10/05/2016
 */
public class BaseXDAOImpl implements BaseXDAO {

	public void createDatabase(String name) throws IOException {
		try (final BaseXClient session = new BaseXClient("localhost", 1984, "admin", "admin")) {
			session.execute("create db " + name);
		}
	}

	public void dropDatabase(String name) throws IOException {
		try (final BaseXClient session = new BaseXClient("localhost", 1984, "admin", "admin")) {
			session.execute("drop db " + name);
		}
	}
}
