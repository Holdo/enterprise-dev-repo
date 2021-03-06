package cz.muni.fi.pb138.webmvc.websocket;

import cz.muni.fi.pb138.api.FileService;
import cz.muni.fi.pb138.api.MetaService;
import cz.muni.fi.pb138.entity.metadata.VersionedMetaFile;
import cz.muni.fi.pb138.entity.metadata.Metas;
import cz.muni.fi.pb138.entity.metadata.VersionedFile;
import cz.muni.fi.pb138.enums.FileType;
import cz.muni.fi.pb138.enums.MetaFileType;
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
 * @author Michal Holič
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
	 * @param args map should contain parameterName to search for of metaParameterType and fileType,
	 *             also exactSearch boolean to specify exact / similarity search type
	 * @return search result as list of latest versions where search hit
	 * @throws IOException
	 * @throws JAXBException
	 */
	public List<VersionedFile> search(Map<String, Object> args) throws IOException, JAXBException {
		String fileType = (String) args.get("fileType");
		String metaParameterType = (String) args.get("metaParameterType");
		fileType = fileType.toUpperCase();
		metaParameterType = metaParameterType.toUpperCase();
		log.debug("Searching for {} of {} type in {} files with exact search {}",
				args.get("parameterName"), metaParameterType, fileType, args.get("exactSearch"));
		return metaService.getFilesFullPathsByMetaParameter(
				FileType.valueOf(fileType),
				MetaParameterType.valueOf(metaParameterType),
				(String ) args.get("parameterName"), (boolean) args.get("exactSearch"));
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
		Integer version = (Integer) args.get("version");
		log.debug("Getting artifact metadata for {} of version {}", args.get("fullPath"), version);
		if (version == null || version == 0) return metaService.getMetaParametersByFileFullPath((String) args.get("fullPath"));
		return metaService.getMetaParametersByFileFullPathAndVersion((String) args.get("fullPath"), version);
	}

	/**
	 * Gets versioned metafile from metadatabase
	 *
	 * @param args map should contain fullPath of original file (not metafile), it's version and metaFileType
	 * @return versioned metafile
	 * @throws IOException
	 */
	public VersionedMetaFile getMetaFile(Map<String, Object> args) throws IOException {
		String metaFileType = (String) args.get("metaFileType");
		log.debug("Getting metafile {} of {} type version {} {}", args.get("fullPath"), metaFileType, args.get("version"));
		return metaService.getMetaFileByFileFullPathAndVersion(
				MetaFileType.valueOf(metaFileType.toUpperCase()),
				(String) args.get("fullPath"),
				(Integer) args.get("version"));
	}
}
