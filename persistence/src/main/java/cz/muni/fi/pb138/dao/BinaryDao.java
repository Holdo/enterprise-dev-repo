package cz.muni.fi.pb138.dao;

import java.io.IOException;

/**
 * Stores and retrieves binary files
 *
 * @author Michal Holič
 */
public interface BinaryDao {

	/**
	 * Retrieves binary file from currently opened database
	 *
	 * @param fullPath full path where to store the file, for example project/main.war
	 * @return binary file
	 * @throws IOException
	 * @see DatabaseDao for opening and closing the database
	 */
	byte[] retrieveBinaryFile(String fullPath) throws IOException;

	/**
	 * Saves binary file to a currently opened database
	 *
	 * @param bytes binary file
	 * @param fullPath full path where to store the file, for example project/main.war
	 * @return result
	 * @throws IOException
	 * @see DatabaseDao for opening and closing the database
	 */
	String saveBinaryFile(byte[] bytes, String fullPath) throws IOException;
}
