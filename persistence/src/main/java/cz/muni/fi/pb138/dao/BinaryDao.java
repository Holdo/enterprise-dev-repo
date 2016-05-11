package cz.muni.fi.pb138.dao;

import java.io.IOException;

/**
 * Created by Michal Holic on 10/05/2016
 */
public interface BinaryDao {

	/**
	 * Retrieves binary file from currently opened database
	 *
	 * @param fullPath full path of file, for example project/main.war
	 * @return binary file
	 * @throws IOException
	 * @see BaseXDao for opening and closing the database
	 */
	byte[] retrieveBinaryFile(String fullPath) throws IOException;

	/**
	 * Saves binary file to a currently opened database
	 *
	 * @param bytes binary file
	 * @param fullPath full path of file, for example project/main.war
	 * @throws IOException
	 * @see BaseXDao for opening and closing the database
	 */
	void saveBinaryFile(byte[] bytes, String fullPath) throws IOException;
}
