package cz.muni.fi.pb138.dao;

import java.io.IOException;

public interface BinaryDao {

	/**
	 * Retrieves binary file from database
	 *
	 * @param fullPath full path of file, for example project/main.war
	 * @return binary file
	 * @throws IOException
	 */
	byte[] retrieveBinaryFile(String fullPath) throws IOException;

	/**
	 * Saves binary file to a database
	 * @param bytes binary file
	 * @param fullPath full path of file, for example project/main.war
	 * @throws IOException
	 */
	void saveBinaryFile(byte[] bytes, String fullPath) throws IOException;
}
