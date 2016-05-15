package cz.muni.fi.pb138.dao;

import cz.muni.fi.pb138.entity.XQueryVariable;
import org.basex.core.BaseXException;

import java.io.IOException;

/**
 * Created by Michal Holic on 10/05/2016
 */
public interface DatabaseDao {

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

	/**
	 * Runs XQuery on the currently opened database (if not specified in XQuery)
	 *
	 * @param xQuery as String
	 * @return result
	 * @throws BaseXException
	 */
	String runXQuery(String xQuery) throws BaseXException;

	/**
	 * Runs XQuery on the currently opened database (if not specified in XQuery)
	 * Supports bind context (if XQueryVariable name is null)
	 * Supports bind variables (if XQueryVariable name is not null)
	 *
	 * @param xQuery as String
	 * @param variables XQueryVariables
	 * @return result
	 * @throws BaseXException
	 */
	String runXQuery(String xQuery, XQueryVariable... variables) throws BaseXException;

	/**
	 * Lists files in specified database in specified path
	 *
	 * @param database to use
	 * @param path to use
	 * @return result
	 * @throws BaseXException
	 */
	String listDirectory(String database, String path) throws BaseXException;

	/**
	 * Deletes the whole directory or a single file in the currently opened database
	 *
	 * @param fullPath to delete
	 * @return result
	 * @throws IOException
	 */
	String deleteFileOrDirectory(String fullPath) throws IOException;
}
