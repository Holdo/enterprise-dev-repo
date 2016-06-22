package cz.muni.fi.pb138.webmvc.websocket;

import cz.muni.fi.pb138.api.FileService;
import cz.muni.fi.pb138.api.MetaService;
import cz.muni.fi.pb138.entity.metadata.Metas;
import cz.muni.fi.pb138.entity.metadata.VersionedFile;
import cz.muni.fi.pb138.enums.FileType;
import cz.muni.fi.pb138.enums.MetaParameterType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
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

	@Autowired
	private MetaService metaService;

	/**
	 * Lists raw database directory non-recursively
	 *
	 * @param args map should contain namespace key to list
	 * @return list of versioned files
	 * @throws IOException
	 */
	public List<VersionedFile> listDir(Map<String, String> args) throws IOException {
		log.debug("Listing {}", args.get("namespace").equals("")? "\\" : args.get("namespace"));
		return fileService.listAllFiles(args.get("namespace"), false, false);
	}

	/**
	 * Lists file versions
	 *
	 * @param args map should countain fullPath for file to list the versions for
	 * @return list of all versions
	 * @throws IOException
	 */
	public List<Integer> listFileVersions(Map<String, String> args) throws IOException {
		log.debug("Listing file versions for {}", args.get("fullPath"));
		return fileService.listFileVersions(args.get("fullPath"));
	}

	/**
	 * Search on database metadata
	 *
	 * @param args map should contain parameterName to search for of metaParameterType and fileType
	 * @return search result as list of latest versions where search hit
	 * @throws IOException
	 * @throws JAXBException
	 */
	public List<VersionedFile> search(Map<String, String> args) throws IOException, JAXBException {
		log.debug("Searching for {} of {} type in {} files", args.get("parameterName"), args.get("metaParameterType"), args.get("fileType"));
		return metaService.getFilesFullPathsByMetaParameter(
				FileType.valueOf(args.get("fileType").toUpperCase()),
				MetaParameterType.valueOf(args.get("metaParameterType").toUpperCase()),
				args.get("parameterName"));
	}

	/**
	 * Gets artifact metadata for specified version
	 *
	 * @param args map should contain fullPath of artifact and it's version as int
	 * @return metadata search result for required artifact
	 * @throws IOException
	 * @throws JAXBException
	 */
	public Metas getArtifactMetadata(Map<String, Object> args) throws IOException, JAXBException {
		log.debug("Getting artifact metadata for {} of version {}", args.get("fullPath"), args.get("version"));
		return metaService.getMetaParametersByFileFullPathAndVersion((String) args.get("fullPath"), (Integer) args.get("version"));
	}
}
