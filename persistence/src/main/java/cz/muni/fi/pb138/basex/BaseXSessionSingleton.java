package cz.muni.fi.pb138.basex;

import java.io.IOException;

/**
 * Created by Michal Holic on 10/05/2016
 */
public class BaseXSessionSingleton {

	private static BaseXClient session = null;

	private BaseXSessionSingleton() {}

	public static BaseXClient getSession() throws IOException {
		if (session == null) {
			session = new BaseXClient("localhost", 1984, "admin", "admin");
		} else {
			if (session.getSocket().isConnected()) return session;
			else session = new BaseXClient("localhost", 1984, "admin", "admin");
		}
		return session;
	}
}
