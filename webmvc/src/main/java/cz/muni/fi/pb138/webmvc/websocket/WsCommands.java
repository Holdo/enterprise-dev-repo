package cz.muni.fi.pb138.webmvc.websocket;

import cz.muni.fi.pb138.api.FileService;
import cz.muni.fi.pb138.entity.metadata.VersionedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Michal Holic on 15/06/2016
 */
@Service
public class WsCommands {

	private static final Logger log = LoggerFactory.getLogger(WsCommands.class);

	@Autowired
	private FileService fileService;

	public List<VersionedFile> listDir(Map<String, String> args) throws IOException {
		return fileService.listAllFiles(args.get("namespace"), false);
	}
}
