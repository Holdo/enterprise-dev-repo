package cz.muni.fi.pb138.dao;

import cz.muni.fi.pb138.basex.BaseXClient;
import cz.muni.fi.pb138.basex.BaseXSessionSingleton;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Michal Holic on 10/05/2016
 */
@Repository
public class BinaryDaoImpl implements BinaryDao {

	private static final Logger log = Logger.getLogger(BinaryDao.class.getName());

	public byte[] retrieveBinaryFile(String fullPath) throws IOException {
		BaseXClient session = BaseXSessionSingleton.getSession();

		byte[] binaryFile = null;

		try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			session.execute("retrieve " + fullPath, baos);
			binaryFile = baos.toByteArray();
		}

		return binaryFile;
	}

	public void saveBinaryFile(byte[] bytes, String fullPath) throws IOException {
		BaseXClient session = BaseXSessionSingleton.getSession();

		try (final ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
			session.store(fullPath, bais);
			log.info(session.info());
		}
	}
}
