package cz.muni.fi.pb138.service;

import cz.muni.fi.pb138.entity.XQueryType;
import cz.muni.fi.pb138.entity.XQueryVariable;
import cz.muni.fi.pb138.entity.metadata.*;
import cz.muni.fi.pb138.enums.FileType;
import cz.muni.fi.pb138.enums.MetaFileType;
import cz.muni.fi.pb138.enums.MetaParameterType;
import cz.muni.fi.pb138.api.*;
import cz.muni.fi.pb138.dao.BinaryDao;
import cz.muni.fi.pb138.dao.DatabaseDao;
import cz.muni.fi.pb138.dao.DocumentDao;
import cz.muni.fi.pb138.service.processing.FileProcessor;
import cz.muni.fi.pb138.service.processing.PathFinder;
import cz.muni.fi.pb138.xquery.XQueryWar;
import cz.muni.fi.pb138.xquery.XQueryWsdl;
import cz.muni.fi.pb138.xquery.XQueryXsd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ondřej Gasior
 */
@Service
public class MetaServiceImpl implements MetaService {

	private static final Logger log = LoggerFactory.getLogger(MetaService.class);
	@Autowired
	private DatabaseDao databaseDao;
	@Autowired
	private BinaryDao binaryDao;
	@Autowired
	private DocumentDao documentDao;
	@Autowired
	private FileProcessor fileProcessor;
	@Autowired
	private PathFinder pathFinder;
	@Autowired
	private FileService fileService;

	@Value("${cz.muni.fi.pb138.xml-db-name}")
	private String XML_DATABASE_NAME;

	@Value("${cz.muni.fi.pb138.meta-db-name}")
	private String META_DATABASE_NAME;

	@Override
	public VersionedMetaFile getMetaFileByFileFullPath(MetaFileType metaFileType, String fullPath) throws IOException {
		fullPath = normalizeFullPath(fullPath);
		String namespace = (fullPath.contains("/")? fullPath.substring(0, fullPath.lastIndexOf("/")) : "");
		databaseDao.openDatabase(META_DATABASE_NAME);
		String list = databaseDao.listDirectory(XML_DATABASE_NAME, namespace);
		int version = pathFinder.getLatestVersion(list, fullPath);
		databaseDao.closeDatabase();
		return getMetaFileByFileFullPathAndVersion(metaFileType, fullPath, version);
	}

	@Override
	public VersionedMetaFile getMetaFileByFileFullPathAndVersion(MetaFileType metaFileType, String fullPath, int version) throws IOException {
		if (version == 0) return getMetaFileByFileFullPath(metaFileType, fullPath);
		VersionedMetaFile output = new VersionedMetaFile();
		if (metaFileType == MetaFileType.WEBXML) {
			databaseDao.openDatabase(META_DATABASE_NAME);
			byte[] metaFile = binaryDao.retrieveBinaryFile(pathFinder.getVersionedPath(fullPath, version, FileType.WAR) + "." + metaFileType.toString());
			databaseDao.closeDatabase();

			output.setVersion(version);
			output.setFullPath(fullPath);
			output.setFile(metaFile);
		}
		return output;
	}


	@Override
	public List<VersionedMetaFile> getAllMetaFilesByMetaFileType(MetaFileType metaFileType, String namespace) throws IOException {
		List<VersionedMetaFile> output = new ArrayList<>();
		databaseDao.openDatabase(META_DATABASE_NAME);
		if (metaFileType == MetaFileType.WEBXML) {
			if (namespace.equals("") || namespace.equals("/") || namespace.equals("\\")) {
				for (VersionedFile vf : fileService.listAllFilesByFileTypeRecursively(namespace, true, true, FileType.WEB_XML)) {
					if (vf.isDirectory()) continue;
					output.add(retrieveMetaFilePathVersionTriplet(vf));
				}
			} else {
				for (VersionedFile vf : fileService.listAllFilesByFileType(namespace, true, true, FileType.WEB_XML)) {
					if (vf.isDirectory()) continue;
					output.add(retrieveMetaFilePathVersionTriplet(vf));
				}
			}
		}
		databaseDao.closeDatabase();
		return output;
	}

	protected VersionedMetaFile retrieveMetaFilePathVersionTriplet(VersionedFile vf) throws IOException {
		VersionedMetaFile triplet = new VersionedMetaFile();
		byte[] metaFile = binaryDao.retrieveBinaryFile(pathFinder.getVersionedPath(vf.getFullPath(), vf.getVersion(), FileType.WEB_XML));
		triplet.setVersion(vf.getVersion());
		triplet.setFullPath(vf.getFullPath());
		triplet.setFile(metaFile);
		return triplet;
	}

	@Override
	public Metas getMetaParametersByFileFullPath(String fullPath) throws IOException, JAXBException {
		fullPath = normalizeFullPath(fullPath);
		String namespace = (fullPath.contains("/")? fullPath.substring(0, fullPath.lastIndexOf("/")) : "");
		databaseDao.openDatabase(XML_DATABASE_NAME);
		String list = databaseDao.listDirectory(XML_DATABASE_NAME, namespace);
		int version = pathFinder.getLatestVersion(list, fullPath);
		databaseDao.closeDatabase();
		return getMetaParametersByFileFullPathAndVersion(fullPath, version);
	}


	@Override
	public Metas getMetaParametersByFileFullPathAndVersion(String fullPath, int version) throws IOException, JAXBException {
		fullPath = normalizeFullPath(fullPath);
		XQueryVariable fullPathVariable = new XQueryVariable("fullPath", fullPath, XQueryType.STRING);
		XQueryVariable versionVariable = new XQueryVariable("version", String.valueOf(version), XQueryType.STRING);
		databaseDao.openDatabase(META_DATABASE_NAME);

		Metas metas;
		if (fullPath.endsWith(".war")) {
			metas = parseQueryResultMetas(databaseDao.runXQuery(XQueryWar.GET_METAS_BY_FILE.toString(), fullPathVariable, versionVariable), WARMetas.class);
		} else if (fullPath.endsWith(".xsd")) {
			metas = parseQueryResultMetas(databaseDao.runXQuery(XQueryXsd.GET_METAS_BY_FILE.toString(), fullPathVariable, versionVariable), XSDMetas.class);
		} else if (fullPath.endsWith(".wsdl")) {
			metas = parseQueryResultMetas(databaseDao.runXQuery(XQueryWsdl.GET_METAS_BY_FILE.toString(), fullPathVariable, versionVariable), WSDLMetas.class);
		} else {
			databaseDao.closeDatabase();
			throw new IOException("Unknown file extension of " + fullPath);
		}
		databaseDao.closeDatabase();
		return metas;
	}

	private <T extends Metas> T parseQueryResultMetas(String queryResult, Class<T> classType) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(classType);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (T) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(queryResult.getBytes(StandardCharsets.UTF_8)));
	}

	private Items parseQueryResultMetaByFile(String queryResult) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Items.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (Items) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(queryResult.getBytes(StandardCharsets.UTF_8)));
	}

	@Override
	public List<VersionedFile> getFilesFullPathsByMetaParameter(FileType fileType, MetaParameterType parameterType, String parameterName, boolean withExactMatch) throws IOException, JAXBException {
		List<VersionedFile> output;
		XQueryVariable nameVariable = new XQueryVariable("name", parameterName, XQueryType.STRING);
		XQueryVariable matchVariable = new XQueryVariable("exactMatch", String.valueOf(withExactMatch), XQueryType.BOOLEAN);
		String queryResult;
		databaseDao.openDatabase(META_DATABASE_NAME);
		switch (fileType) {
			case WAR:
				if (parameterType.equals(MetaParameterType.FILTER)) {
					queryResult = databaseDao.runXQuery(XQueryWar.GET_FILES_BY_FILTER.toString(), nameVariable,matchVariable);
				} else if (parameterType.equals(MetaParameterType.LISTENER)) {
					queryResult = databaseDao.runXQuery(XQueryWar.GET_FILES_BY_LISTENER.toString(), nameVariable,matchVariable);
				} else {
					throw new IOException("Unknown meta parameter " + parameterType.toString());
				}
				break;
			case WSDL:
				if (parameterType.equals(MetaParameterType.OPERATION)) {
					queryResult = databaseDao.runXQuery(XQueryWsdl.GET_FILES_BY_OPERATION.toString(), nameVariable,matchVariable);
				} else if (parameterType.equals(MetaParameterType.REQUEST)) {
					queryResult = databaseDao.runXQuery(XQueryWsdl.GET_FILES_BY_REQUEST.toString(), nameVariable,matchVariable);
				} else if (parameterType.equals(MetaParameterType.RESPONSE)) {
					queryResult = databaseDao.runXQuery(XQueryWsdl.GET_FILES_BY_RESPONSE.toString(), nameVariable,matchVariable);
				} else {
					throw new IOException("Unknown meta parameter " + parameterType.toString());
				}
				break;
			case XSD:
				if (parameterType.equals(MetaParameterType.ATTRIBUTE)) {
					queryResult = databaseDao.runXQuery(XQueryXsd.GET_FILES_BY_ATTRIBUTE.toString(), nameVariable,matchVariable);
				} else if (parameterType.equals(MetaParameterType.ELEMENT)) {
					queryResult = databaseDao.runXQuery(XQueryXsd.GET_FILES_BY_ELEMENT.toString(), nameVariable,matchVariable);
				} else if (parameterType.equals(MetaParameterType.COMPLEXTYPE)) {
					queryResult = databaseDao.runXQuery(XQueryXsd.GET_FILES_BY_COMPLEXTYPE.toString(), nameVariable,matchVariable);
				} else if (parameterType.equals(MetaParameterType.SIMPLETYPE)) {
					queryResult = databaseDao.runXQuery(XQueryXsd.GET_FILES_BY_SIMPLETYPE.toString(), nameVariable,matchVariable);
				} else {
					throw new IOException("Unknown meta parameter " + parameterType.toString());
				}
				break;
			default:
				databaseDao.closeDatabase();
				throw new IOException("Unknown file type " + fileType.toString());
		}
		databaseDao.closeDatabase();
		output = parseFileSearchQueryBro(queryResult);
		return output;
	}

	private List<VersionedFile> parseFileSearchQueryBro(String queryResult) throws JAXBException {
		List<VersionedFile> output = new ArrayList<>();
		JAXBContext jaxbContext = JAXBContext.newInstance(SearchResult.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		SearchResult items = (SearchResult) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(queryResult.getBytes(StandardCharsets.UTF_8)));
		if (items.getFile() == null) return output;
		for (SearchFile s : items.getFile()) {
			output.add(new VersionedFile(s.getFullPath(), Integer.valueOf(s.getVersion()), false));
		}
		return output;
	}

	@Override
	public List<Items> getAllMetaParametersByMetaParameterType(MetaParameterType parameterType) throws IOException, JAXBException {
		List<Items> items = new ArrayList<>();

		databaseDao.openDatabase(META_DATABASE_NAME);

		String queryResult;
		switch (parameterType) {
			case ATTRIBUTE:
				queryResult = databaseDao.runXQuery(XQueryXsd.GET_ATTRIBUTES.toString());
				items.add(parseQueryResultMetaByFile(queryResult));
				break;
			case ELEMENT:
				queryResult = databaseDao.runXQuery(XQueryXsd.GET_ELEMENTS.toString());
				items.add(parseQueryResultMetaByFile(queryResult));
				break;
			case COMPLEXTYPE:
				queryResult = databaseDao.runXQuery(XQueryXsd.GET_COMPLEX_TYPES.toString());
				items.add(parseQueryResultMetaByFile(queryResult));
				break;
			case SIMPLETYPE:
				queryResult = databaseDao.runXQuery(XQueryXsd.GET_SIMPLE_TYPES.toString());
				items.add(parseQueryResultMetaByFile(queryResult));
				break;
			case OPERATION:
				queryResult = databaseDao.runXQuery(XQueryWsdl.GET_OPERATIONS.toString());
				items.add(parseQueryResultMetaByFile(queryResult));
				break;
			case REQUEST:
				queryResult = databaseDao.runXQuery(XQueryWsdl.GET_REQUESTS.toString());
				items.add(parseQueryResultMetaByFile(queryResult));
				break;
			case RESPONSE:
				queryResult = databaseDao.runXQuery(XQueryWsdl.GET_RESPONSES.toString());
				items.add(parseQueryResultMetaByFile(queryResult));
				break;
			case LISTENER:
				queryResult = databaseDao.runXQuery(XQueryWar.GET_FILTERS.toString());
				items.add(parseQueryResultMetaByFile(queryResult));
				break;
			case FILTER:
				queryResult = databaseDao.runXQuery(XQueryWar.GET_LISTENERS.toString());
				items.add(parseQueryResultMetaByFile(queryResult));
				break;
			default:
				databaseDao.closeDatabase();
				throw new IOException("Unknown file attribute");
		}
		databaseDao.closeDatabase();
		return items;
	}

	protected String normalizeFullPath(String fullPath) {
		fullPath = Paths.get(fullPath).toString();
		fullPath= fullPath.replaceAll("\\\\", "/");
		while (fullPath.startsWith("/")) fullPath = fullPath.substring(1, fullPath.length());
		if (fullPath.endsWith("/")) fullPath = fullPath.substring(0, fullPath.length() - 1);
		return fullPath;
	}

}
