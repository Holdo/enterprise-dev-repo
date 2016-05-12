package cz.muni.fi.pb138.dao;

import cz.muni.fi.pb138.basex.BaseXContext;
import org.basex.core.cmd.Retrieve;
import org.basex.core.cmd.Store;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private BaseXContext dbCtx;

	private static final Logger log = Logger.getLogger(BinaryDao.class.getName());

	public byte[] retrieveBinaryFile(String fullPath) throws IOException {
		byte[] binaryFile = null;

		try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			new Retrieve(fullPath).execute(dbCtx.getContext(), baos);
			binaryFile = baos.toByteArray();
		}

		return binaryFile;
	}

	public String saveBinaryFile(byte[] bytes, String fullPath) throws IOException {
		try (final ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
			Store storeCmd = new Store(fullPath);
			storeCmd.setInput(bais);
			return storeCmd.execute(dbCtx.getContext());
		}
	}
}
