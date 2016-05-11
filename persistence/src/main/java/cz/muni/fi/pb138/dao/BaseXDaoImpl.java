package cz.muni.fi.pb138.dao;

import cz.muni.fi.pb138.basex.BaseXClient;
import cz.muni.fi.pb138.basex.BaseXSessionSingleton;
import org.springframework.stereotype.Repository;

import java.io.IOException;

/**
 * Created by Michal Holic on 10/05/2016
 */
@Repository
public class BaseXDaoImpl implements BaseXDao {

	public String openDatabase(String name) throws IOException {
		BaseXClient session = BaseXSessionSingleton.getSession();
		return session.execute("open " + name);
	}

	public String closeDatabase() throws IOException {
		BaseXClient session = BaseXSessionSingleton.getSession();
		return session.execute("close");
	}

	public String dropDatabase(String name) throws IOException {
		BaseXClient session = BaseXSessionSingleton.getSession();
		return session.execute("drop db " + name);
	}
}
