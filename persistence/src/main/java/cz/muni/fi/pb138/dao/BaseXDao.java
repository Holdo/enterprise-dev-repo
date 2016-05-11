package cz.muni.fi.pb138.dao;

import java.io.IOException;

/**
 * Created by Michal Holic on 10/05/2016
 */
public interface BaseXDao {

	/**
	 * Opens database
	 *
	 * @param name of the database
	 * @return result
	 * @throws IOException
	 */
	String openDatabase(String name) throws IOException;

	/**
	 * Closes currently opened database
	 *
	 * @return result
	 * @throws IOException
	 */
	String closeDatabase() throws IOException;

	/**
	 * Drops database (careful!)
	 *
	 * @param name of the database
	 * @return result
	 * @throws IOException
	 */
	String dropDatabase(String name) throws IOException;
}
