package cz.muni.fi.pb138.service;

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
import cz.muni.fi.pb138.entity.metadata.PathVersionPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gasior
 */
@Service
public class MetaServiceImpl implements MetaService {

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
	public List<PathVersionPair> getFilesFullPathsByMetaParameter(FileType fileType, MetaParameterType parameterType, String namespace, String parameterName) throws IOException {


		// TODO


		return null;
	}

	@Override
	public List<PathVersionPair> getAllMetaParametersByMetaParameterType(MetaParameterType parameterType, String namespace) throws IOException {


		// TODO

		return null;

	}

	@Override
	public List<MetaFilePathVersionTriplet> getAllMetaFilesByMetaFileType(MetaFileType metaFileType, String namespace) throws IOException {
		List<MetaFilePathVersionTriplet> output = new ArrayList<>();
		databaseDao.openDatabase(META_DATABASE_NAME);
		if (metaFileType == MetaFileType.WEBXML) {
			for (PathVersionPair path : fileService.listAllFilesByFileType(namespace, FileType.WAR, true)) {
				MetaFilePathVersionTriplet triplet = new MetaFilePathVersionTriplet();
				byte[] metaFile = binaryDao.retrieveBinaryFile(pathFinder.getVersionedPath(path.getFullPath(), path.getVersion(), FileType.WAR) + metaFileType.name());
				triplet.setVersion(path.getVersion());
				triplet.setFullPath(path.getFullPath());
				triplet.setFile(metaFile);
			}
		}
		databaseDao.closeDatabase();

		return output;
	}

	@Override
	public MetaFilePathVersionTriplet getMetaFileByFileFullPath(MetaFileType metaFileType, String fullPath) throws IOException {
		return null;
	}

	@Override
	public MetaFilePathVersionTriplet getMetaFileByFileFullPathAndVersion(MetaFileType metaFileType, String fullPath, int version) throws IOException {
		MetaFilePathVersionTriplet output = new MetaFilePathVersionTriplet();
		databaseDao.openDatabase(META_DATABASE_NAME);
		byte[] metaFile = binaryDao.retrieveBinaryFile(pathFinder.getVersionedPath(fullPath, version, FileType.WAR) + "." + metaFileType.toString());
		databaseDao.closeDatabase();

		output.setVersion(version);
		output.setFullPath(fullPath);
		output.setFile(metaFile);
		return output;
	}

	@Override
	public List<PathVersionPair> getMetaParametersByFileFullPath(MetaParameterType parameterType, String fullPath) throws IOException {
		List<PathVersionPair> output = new ArrayList<>();


		// TODO


		return output;
	}

	@Override
	public List<PathVersionPair> getMetaParametersByFileFullPathAndVersion(MetaParameterType parameterType, String fullPath, String version) throws IOException {
		List<PathVersionPair> output = new ArrayList<>();


		// TODO


		return output;
	}


}
