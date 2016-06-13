package cz.muni.fi.pb138.dao;

import cz.muni.fi.pb138.basex.BaseXContext;
import org.basex.core.cmd.Retrieve;
import org.basex.core.cmd.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Michal Holic on 10/05/2016
 */
@Repository
public class BinaryDaoImpl implements BinaryDao {

	private static final Logger log = LoggerFactory.getLogger(BinaryDao.class);

	@Autowired
	private BaseXContext dbCtx;

	public byte[] retrieveBinaryFile(String fullPath) throws IOException {
		log.debug("Retrieving binary file from {}", fullPath);
		byte[] binaryFile;
		try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			new Retrieve(fullPath).execute(dbCtx.getContext(), baos);
			binaryFile = baos.toByteArray();
		}
		return binaryFile;
	}

	public String saveBinaryFile(byte[] bytes, String fullPath) throws IOException {
		log.debug("Saving {} bytes binary file to {}", bytes.length, fullPath);
		try (final ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
			Store storeCmd = new Store(fullPath);
			storeCmd.setInput(bais);
			return storeCmd.execute(dbCtx.getContext());
		}
	}
}
