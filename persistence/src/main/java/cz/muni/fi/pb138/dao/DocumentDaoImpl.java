package cz.muni.fi.pb138.dao;

import cz.muni.fi.pb138.basex.BaseXContext;
import org.basex.core.BaseXException;
import org.basex.core.cmd.Add;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by Michal Holic on 12/05/2016
 */
@Repository
public class DocumentDaoImpl implements DocumentDao {

	private static final Logger log = LoggerFactory.getLogger(DocumentDao.class);

	@Autowired
	private BaseXContext dbCtx;

	public String addDocument(byte[] bytes, String fullPath) throws IOException {
		log.debug("Saving {} bytes document as {}", bytes.length, fullPath);
		try (final ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
			Add addCmd = new Add(fullPath);
			addCmd.setInput(bais);
			return addCmd.execute(dbCtx.getContext());
		}
	}

	public String addDocument(String input, String fullPath) throws BaseXException {
		log.debug("Saving {} length string document as {}", input.length(), fullPath);
		return new Add(fullPath, input).execute(dbCtx.getContext());
	}
}
