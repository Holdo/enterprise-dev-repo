package cz.muni.fi.pb138.service;

import cz.muni.fi.pb138.api.FileService;
import cz.muni.fi.pb138.entity.FileBase;
import cz.muni.fi.pb138.enums.FileType;
import cz.muni.fi.pb138.enums.MetaFileType;
import cz.muni.fi.pb138.dao.BinaryDao;
import cz.muni.fi.pb138.dao.DatabaseDao;
import cz.muni.fi.pb138.dao.DocumentDao;
import cz.muni.fi.pb138.service.processing.FileProcessor;
import cz.muni.fi.pb138.service.processing.PathFinder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;

import cz.muni.fi.pb138.entity.metadata.VersionedFile;
import cz.muni.fi.pb138.entity.war.WarFile;
import cz.muni.fi.pb138.entity.wsdl.WsdlFile;
import cz.muni.fi.pb138.entity.xsd.XsdFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author gasior
 */
@Service
public class FileServiceImpl implements FileService {

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

	@Value("${cz.muni.fi.pb138.xml-db-name}")
	private String XML_DATABASE_NAME;

	@Value("${cz.muni.fi.pb138.meta-db-name}")
	private String META_DATABASE_NAME;

	private final String META_FILE_SUFFIX = ".xml";
	private final String[] supportedFiles = {".war", ".xsd", ".wsdl"};
	private String databaseRawFileSystemRoot = null;

	@PostConstruct
	public void setDatabaseRawFileSystemRoot() {
		databaseRawFileSystemRoot = databaseDao.getDatabaseRawFileSystemRoot();
	}

	@Override
	public void saveFile(String fullPath, byte[] fileBytes) throws IOException, SAXException, ParserConfigurationException, DataFormatException, JAXBException {
		log.debug("Received {} bytes file as {}", fileBytes.length, fullPath);
		FileBase file = null;

		for (String s : supportedFiles) {
			if (fullPath.endsWith(s)) {
				if (s.equals(".war")) {
					file = fileProcessor.processWar(fullPath, fileBytes);
				}
				if (s.equals(".xsd")) {
					file = fileProcessor.processXsd(fullPath, fileBytes);
				}
				if (s.equals(".wsdl")) {
					file = fileProcessor.processWsdl(fullPath, fileBytes);
				}
			}
		}

		if (null == file) {
			throw new IOException("Invalid file: " + fullPath);
		}

		databaseDao.openDatabase(XML_DATABASE_NAME);
		String vNamespace = fullPath.substring(0, fullPath.lastIndexOf(File.separator) + 1);
		String list = databaseDao.listDirectory(XML_DATABASE_NAME, vNamespace);
		int version = pathFinder.getLatestVersion(list, fullPath) + 1;

		file.setVersion(version);
		String vPath = pathFinder.getVersionedPath(fullPath, version, file.getType());
		binaryDao.saveBinaryFile(file.getFile(), vPath);
		databaseDao.closeDatabase();

		databaseDao.openDatabase(META_DATABASE_NAME);
		documentDao.addDocument(file.getMeta(), pathFinder.getVersionedPath(fullPath, version, file.getType()) + META_FILE_SUFFIX);
		for (MetaFileType type : file.getMetaFiles().keySet()) {
			String metaFilePath = vPath + "." + type.toString();
			binaryDao.saveBinaryFile(file.getMetaFiles().get(type), metaFilePath);
		}
		databaseDao.closeDatabase();

	}

	@Override
	public void deleteFile(String fullPath) throws IOException {
		databaseDao.openDatabase(XML_DATABASE_NAME);
		String list = databaseDao.listDirectory(XML_DATABASE_NAME, fullPath.substring(0, fullPath.lastIndexOf(File.separator)));
		int version = pathFinder.getLatestVersion(list, fullPath);
		databaseDao.closeDatabase();
		deleteFile(fullPath, version);
	}

	@Override
	public void deleteFile(String fullPath, int version) throws IOException {
		if (version == 0) {
			throw new IOException("Not existing version");
		}
		FileBase f = getFileInstance(fullPath, version);
		String deletePath = pathFinder.getVersionedPath(fullPath, version, f.getType());
		databaseDao.openDatabase(XML_DATABASE_NAME);
		databaseDao.deleteFileOrDirectory(deletePath);
		databaseDao.closeDatabase();
		databaseDao.openDatabase(META_DATABASE_NAME);
		databaseDao.deleteFileOrDirectory(deletePath + META_FILE_SUFFIX);
		FileBase file = getFileInstance(fullPath, version);
		for (MetaFileType type : file.getMetaFiles().keySet()) {
			databaseDao.deleteFileOrDirectory(deletePath + type.name());
		}
		databaseDao.closeDatabase();
	}

	@Override
	public byte[] getFileByFullPath(String fullPath) throws IOException {
		databaseDao.openDatabase(XML_DATABASE_NAME);
		String list = databaseDao.listDirectory(XML_DATABASE_NAME, fullPath.substring(0, fullPath.lastIndexOf(File.separator)));
		int version = pathFinder.getLatestVersion(list, fullPath);
		databaseDao.closeDatabase();
		return getFileByFullPathAndVersion(fullPath, version);
	}

	@Override
	public byte[] getFileByFullPathAndVersion(String fullPath, int version) throws IOException {
		log.debug("Retrieving binary file version {} at {}", version, fullPath);
		FileBase f = getFileInstance(fullPath, version);
		String path = pathFinder.getVersionedPath(fullPath, version, f.getType());
		databaseDao.openDatabase(XML_DATABASE_NAME);
		byte[] output = binaryDao.retrieveBinaryFile(path);
		databaseDao.closeDatabase();
		return output;
	}

	@Override
	public List<Integer> listFileVersions(String fullPath) throws IOException {
		databaseDao.openDatabase(XML_DATABASE_NAME);
		String list = databaseDao.listDirectory(XML_DATABASE_NAME, fullPath.substring(0, fullPath.lastIndexOf(File.separator)));
		databaseDao.closeDatabase();
		return pathFinder.getAllVersions(list, fullPath);
	}

	@Override
	public List<VersionedFile> listAllFiles(String namespace, boolean allVersions) throws IOException {
		namespace = sanitizeNamespace(namespace);
		return pathFinder.parseFileList(getFileSystemDir(namespace).listFiles(), namespace, allVersions, null);
	}

	@Override
	public List<VersionedFile> listAllFilesByFileType(String namespace, boolean allVersions, FileType fileType) throws IOException {
		namespace = sanitizeNamespace(namespace);
		return pathFinder.parseFileList(getFileSystemDir(namespace).listFiles(), namespace, allVersions, fileType);
	}

	protected String sanitizeNamespace(String namespace) {
		if (namespace.startsWith("/") || namespace.startsWith("\\")) namespace = namespace.substring(1);
		return namespace;
	}

	protected File getFileSystemDir(String namespace) {
		File dir = new File(databaseRawFileSystemRoot + namespace);
		if (!dir.isDirectory()) {
			try {
				throw new IOException("Not a directory: " + dir.getCanonicalPath());
			} catch (IOException e) {
				log.error("Exception during reading path of file system directory for {}", namespace, e);
			}
		}
		return dir;
	}

	protected String getDatabaseRawFileSystemRoot() {
		return this.databaseRawFileSystemRoot;
	}

	protected FileBase getFileInstance(String fullPath, int version) throws IOException {
		if (fullPath.endsWith(".war")) {
			WarFile file = new WarFile();
			file.setNameVersionPair(new VersionedFile(fullPath, version));
			return file;
		}
		if (fullPath.endsWith(".xsd")) {
			XsdFile file = new XsdFile();
			file.setNameVersionPair(new VersionedFile(fullPath, version));
			return file;
		}
		if (fullPath.endsWith(".wsdl")) {
			WsdlFile file = new WsdlFile();
			file.setNameVersionPair(new VersionedFile(fullPath, version));
			return file;
		}
		throw new IOException("Unknown file extension of " + fullPath);
	}

}
