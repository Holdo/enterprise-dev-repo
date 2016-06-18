package cz.muni.fi.pb138.service;

import cz.muni.fi.pb138.entity.XQueryType;
import cz.muni.fi.pb138.entity.XQueryVariable;
import cz.muni.fi.pb138.entity.metadata.Items;
import cz.muni.fi.pb138.entity.metadata.RootItem;
import cz.muni.fi.pb138.enums.FileType;
import cz.muni.fi.pb138.enums.MetaFileType;
import cz.muni.fi.pb138.enums.MetaParameterType;
import cz.muni.fi.pb138.api.*;
import cz.muni.fi.pb138.dao.BinaryDao;
import cz.muni.fi.pb138.dao.DatabaseDao;
import cz.muni.fi.pb138.dao.DocumentDao;
import cz.muni.fi.pb138.service.processing.FileProcessor;
import cz.muni.fi.pb138.service.processing.PathFinder;
import cz.muni.fi.pb138.entity.metadata.MetaFilePathVersionTriplet;
import cz.muni.fi.pb138.entity.metadata.VersionedFile;
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
 * @author gasior
 */
@Service
public class MetaServiceImpl implements MetaService {

	private static final Logger log = LoggerFactory.getLogger(FileService.class);
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

	protected String normalizeFullPath(String fullPath) {
		return Paths.get(fullPath).toString();
	}
	@Override
	public MetaFilePathVersionTriplet getMetaFileByFileFullPath(MetaFileType metaFileType, String fullPath) throws IOException {
		fullPath = normalizeFullPath(fullPath);
		databaseDao.openDatabase(META_DATABASE_NAME);
		String list = databaseDao.listDirectory(XML_DATABASE_NAME, fullPath.substring(0, fullPath.lastIndexOf(File.separator)));
		int version = pathFinder.getLatestVersion(list, fullPath);
		databaseDao.closeDatabase();
		return getMetaFileByFileFullPathAndVersion(metaFileType, fullPath, version);

	}

	@Override
	public MetaFilePathVersionTriplet getMetaFileByFileFullPathAndVersion(MetaFileType metaFileType, String fullPath, int version) throws IOException {

		MetaFilePathVersionTriplet output = new MetaFilePathVersionTriplet();
		if (metaFileType == MetaFileType.WEBXML){
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
	public List<MetaFilePathVersionTriplet> getAllMetaFilesByMetaFileType(MetaFileType metaFileType, String namespace) throws IOException {
		List<MetaFilePathVersionTriplet> output = new ArrayList<>();
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

	public MetaFilePathVersionTriplet retrieveMetaFilePathVersionTriplet(VersionedFile vf) throws IOException {
		MetaFilePathVersionTriplet triplet = new MetaFilePathVersionTriplet();
		byte[] metaFile = binaryDao.retrieveBinaryFile(pathFinder.getVersionedPath(vf.getFullPath(), vf.getVersion(), FileType.WEB_XML));
		triplet.setVersion(vf.getVersion());
		triplet.setFullPath(vf.getFullPath());
		triplet.setFile(metaFile);
		return triplet;
	}

	@Override
	public Items getMetaParametersByFileFullPath(String fullPath) throws IOException, JAXBException {
		fullPath = normalizeFullPath(fullPath);
		databaseDao.openDatabase(XML_DATABASE_NAME);
		String list = databaseDao.listDirectory(XML_DATABASE_NAME, fullPath.substring(0, fullPath.lastIndexOf(File.separator)));
		int version = pathFinder.getLatestVersion(list, fullPath);
		databaseDao.closeDatabase();
		return getMetaParametersByFileFullPathAndVersion(fullPath, version);
	}


	@Override
	public Items getMetaParametersByFileFullPathAndVersion(String fullPath, int version) throws IOException, JAXBException {
		databaseDao.openDatabase(META_DATABASE_NAME);
		String queryResult = "";
        XQueryVariable xQueryVariable;
        String path;
		MetaParameterType parameterType = MetaParameterType.ATTRIBUTE;
		switch (parameterType) {
			case ATTRIBUTE:
                path = pathFinder.getVersionedPath(fullPath, version, FileType.XSD);
				queryResult = databaseDao.runXQuery(XQueryXsd.GET_ATTRIBUTES.toString());
				break;
			case SIMPLETYPE:
                path = pathFinder.getVersionedPath(fullPath, version, FileType.XSD);
				xQueryVariable = new XQueryVariable("file", path+".xml", XQueryType.STRING);
				databaseDao.runXQuery(XQueryXsd.GET_SIMPLE_TYPES.toString(),xQueryVariable);
				break;
			case COMPLEXTYPE:
                path = pathFinder.getVersionedPath(fullPath, version, FileType.XSD);
				xQueryVariable = new XQueryVariable("file", path+".xml", XQueryType.STRING);
				databaseDao.runXQuery(XQueryXsd.GET_COMPLEX_TYPES.toString(),xQueryVariable);
				break;
			case ELEMENT:
                path = pathFinder.getVersionedPath(fullPath, version, FileType.XSD);
				xQueryVariable = new XQueryVariable("file", path+".xml", XQueryType.STRING);
				databaseDao.runXQuery(XQueryXsd.GET_ELEMENTS.toString(),xQueryVariable);
				break;
			case OPERATION:
				path = pathFinder.getVersionedPath(fullPath, version, FileType.WSDL);
				xQueryVariable = new XQueryVariable("file", path+".xml", XQueryType.STRING);
				databaseDao.runXQuery(XQueryWsdl.GET_OPERATIONS.toString(),xQueryVariable);
				break;
			case REQUEST:
				path = pathFinder.getVersionedPath(fullPath, version, FileType.WSDL);
				xQueryVariable = new XQueryVariable("file", path+".xml", XQueryType.STRING);
				databaseDao.runXQuery(XQueryWsdl.GET_REQUESTS.toString(),xQueryVariable);
				break;
			case RESPONSE:
				path = pathFinder.getVersionedPath(fullPath, version, FileType.WSDL);
				xQueryVariable = new XQueryVariable("file", path+".xml", XQueryType.STRING);
				databaseDao.runXQuery(XQueryWsdl.GET_RESPONSES.toString(),xQueryVariable);
				break;
			case LISTENER:
				path = pathFinder.getVersionedPath(fullPath, version, FileType.WAR);
				xQueryVariable = new XQueryVariable("file", path+".xml", XQueryType.STRING);
				databaseDao.runXQuery(XQueryWar.GET_LISTENERS.toString(),xQueryVariable);
				break;
			case FILTER:
				path = pathFinder.getVersionedPath(fullPath, version, FileType.WAR);
				xQueryVariable = new XQueryVariable("file", path+".xml", XQueryType.STRING);
				databaseDao.runXQuery(XQueryWar.GET_FILTERS.toString(),xQueryVariable);
				break;
			default:
				break;

		}
		databaseDao.closeDatabase();


		return parseQueryResult(queryResult, version, fullPath);
	}
	private Items parseQueryResult(String queryResult, int version, String fullPath) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Items.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Items items = (Items) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(queryResult.getBytes(StandardCharsets.UTF_8)));
		return items;
	}

	@Override
	public List<VersionedFile> getFilesFullPathsByMetaParameter(FileType fileType, MetaParameterType parameterType, String parameterName) throws IOException {


		List<VersionedFile> output = new ArrayList<>();



		return output;
	}

	@Override
	public List<VersionedFile> getAllMetaParametersByMetaParameterType(MetaParameterType parameterType, String namespace) throws IOException {


		List<VersionedFile> output = new ArrayList<>();



		return output;

	}


}
