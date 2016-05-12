package cz.muni.fi.pb138.dao;

import org.basex.core.BaseXException;

import java.io.IOException;

/**
 * Created by Michal Holic on 12/05/2016
 */
public interface DocumentDao {

	/**
	 * Stores byte[] document in the currently opened database
	 *
	 * @param bytes binary file
	 * @param fullPath full path where to store the document, for example project/doc.xml
	 * @return result
	 * @throws IOException
	 */
	String addDocument(byte[] bytes, String fullPath) throws IOException;

	/**
	 * Stores document in the currently opened database, see input parameter
	 *
	 * @param input XML String or input file/directory or remote URL
	 * @param fullPath full path where to store the document, for example project/doc.xml
	 * @return result
	 * @throws BaseXException
	 */
	String addDocument(String input, String fullPath) throws BaseXException;
}
