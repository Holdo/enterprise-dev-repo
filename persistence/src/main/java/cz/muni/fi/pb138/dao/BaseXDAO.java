package cz.muni.fi.pb138.dao;

import java.io.IOException;

/**
 * Created by Michal Holic on 10/05/2016
 */
public interface BaseXDao {

	/**
	 * Creates database
	 *
	 * @param name of the database
	 * @throws IOException
	 */
	void createDatabase(String name) throws IOException;

	/**
	 * Drops database
	 *
	 * @param name of the database
	 * @throws IOException
	 */
	void dropDatabase(String name) throws IOException;
}
