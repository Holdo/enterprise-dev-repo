package cz.muni.fi.pb138.service;

import cz.muni.fi.pb138.api.FileService;
import cz.muni.fi.pb138.api.FileType;
import cz.muni.fi.pb138.api.MetaFileType;
import cz.muni.fi.pb138.dao.BinaryDao;
import cz.muni.fi.pb138.dao.DatabaseDao;
import cz.muni.fi.pb138.dao.DocumentDao;
import cz.muni.fi.pb138.service.processing.FileProcessor;
import cz.muni.fi.pb138.service.processing.PathFinder;
import cz.muni.fi.pb138.service.processing.entity.FileBase;
import cz.muni.fi.pb138.service.processing.entity.PathVersionPair;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.zip.DataFormatException;

import cz.muni.fi.pb138.service.processing.entity.WarFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

/**
 * @author gasior
 */
@Service
public class FileServiceImpl implements FileService {

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

    public final String FILE_DATABASE_NAME = "artifacts";
    public final String META_DATABASE_NAME = "metadata";
    public final String META_FILE_SUFFIX = ".xml";
    public final String PATHFINDER_LOCATION = "/pathfinder.xml";

    @Override
    public void saveFile(String fullPath, byte[] fileBytes) throws IOException, SAXException, ParserConfigurationException, DataFormatException {
        FileBase file = null;
        HashMap<String, FileBase> supportedFiles = new HashMap<>();
        supportedFiles.put(".war", fileProcessor.processWar(fullPath, fileBytes));
        supportedFiles.put(".xsd", fileProcessor.processXsd(fullPath, fileBytes));
        supportedFiles.put(".wsdl", fileProcessor.processWsdl(fullPath, fileBytes));

        for (String s : supportedFiles.keySet()) {
            if (fullPath.endsWith(s)) {
                file = supportedFiles.get(s);
            }
        }

        if (null == file) {
            throw new IOException("Invalid file: " + fullPath);
        }

        String savePath = pathFinder.addFile(fullPath, file.getType());
        file.setVersion(pathFinder.getLastestVersion(fullPath));
        databaseDao.openDatabase(FILE_DATABASE_NAME);
        binaryDao.saveBinaryFile(pathFinder.getMeta(),PATHFINDER_LOCATION);
        binaryDao.saveBinaryFile(file.getFile(),savePath);
        databaseDao.closeDatabase();
        databaseDao.openDatabase(META_DATABASE_NAME);
        documentDao.addDocument(file.getMeta(), savePath+META_FILE_SUFFIX);

        for (MetaFileType type : file.getMetaFiles().keySet()) {
            documentDao.addDocument(file.getMetaFiles().get(type), savePath+type.name());
        }
        databaseDao.closeDatabase();


    }

    @Override
    public void deleteFile(String fullPath) throws IOException {
        deleteFile(fullPath, pathFinder.getLastestVersion(fullPath));
    }

    @Override
    public void deleteFile(String fullPath, int version) throws IOException {
        if(version == 0) {
            throw new IOException("Not existing version");
        }
        String deletePath = pathFinder.deleteFile(fullPath,version);
        databaseDao.openDatabase(FILE_DATABASE_NAME);
        databaseDao.deleteFileOrDirectory(deletePath);
        binaryDao.saveBinaryFile(pathFinder.getMeta(),PATHFINDER_LOCATION);
        databaseDao.closeDatabase();
        databaseDao.openDatabase(META_DATABASE_NAME);
        databaseDao.deleteFileOrDirectory(deletePath + META_FILE_SUFFIX);
        FileBase file = getFileInstance(fullPath, version);
        for (MetaFileType type : file.getMetaFiles().keySet()) {
            databaseDao.deleteFileOrDirectory(deletePath+type.name());
        }
        databaseDao.closeDatabase();
    }

    @Override
    public byte[] getFileByFullPath(String fullPath) throws IOException {
        return getFileByFullPathAndVersion(fullPath, pathFinder.getLastestVersion(fullPath));
    }

    @Override
    public byte[] getFileByFullPathAndVersion(String fullPath, int version) throws IOException {
        databaseDao.openDatabase(FILE_DATABASE_NAME);
        byte[] output = binaryDao.retrieveBinaryFile(pathFinder.getVersionedPath(fullPath,version));
        databaseDao.closeDatabase();
        return output;
    }

    @Override
    public List<Integer> getFileVersions(String fullPath) throws IOException {
       return pathFinder.getAllVersions(fullPath);
    }

    @Override
    public List<PathVersionPair> getAllFilesByFileType(FileType fileType, String namespace) throws IOException {
        return pathFinder.getAllFilesByFileType(fileType,namespace);
    }

    private FileBase getFileInstance(String fullPath, int version) throws IOException {
        if (fullPath.endsWith(".war")) {
            WarFile file = new WarFile();
            file.setNameVersionPair(new PathVersionPair(fullPath, version));
        }
        throw new IOException();
    }


}
