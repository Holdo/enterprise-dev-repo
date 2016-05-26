package cz.muni.fi.pb138.service;

import cz.muni.fi.pb138.api.FileService;
import cz.muni.fi.pb138.api.FileType;
import cz.muni.fi.pb138.api.MetaFileType;
import cz.muni.fi.pb138.dao.BinaryDao;
import cz.muni.fi.pb138.dao.DatabaseDao;
import cz.muni.fi.pb138.dao.DocumentDao;
import cz.muni.fi.pb138.service.processing.FileProcessor;
import cz.muni.fi.pb138.service.processing.PathFinder;
import cz.muni.fi.pb138.service.processing.entity.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.zip.DataFormatException;

import net.xqj.basex.bin.F;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
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

    private final String FILE_DATABASE_NAME = "artifacts";
    private final String META_DATABASE_NAME = "metadata";
    private final String META_FILE_SUFFIX = ".xml";
    private final String[] supportedFiles = {".war", ".xsd", ".wsdl"};

    @Override
    public void saveFile(String fullPath, byte[] fileBytes) throws IOException, SAXException, ParserConfigurationException, DataFormatException, JAXBException {
        FileBase file = null;


        for (String s : supportedFiles) {
            if (fullPath.endsWith(s)) {
                if(s.equals(".war")) {
                    file = fileProcessor.processWar(fullPath,fileBytes);
                }
                if(s.equals(".xsd")) {
                    file = fileProcessor.processXsd(fullPath,fileBytes);
                }
                if(s.equals(".wsdl")) {
                    file = fileProcessor.processWsdl(fullPath,fileBytes);
                }
            }
        }

        if (null == file) {
            throw new IOException("Invalid file: " + fullPath);
        }

        databaseDao.openDatabase(FILE_DATABASE_NAME);
        String vNamespace = fullPath.substring(0,fullPath.lastIndexOf("/")+1);
        String list = databaseDao.listDirectory(FILE_DATABASE_NAME, vNamespace);
        int version = pathFinder.getLastVersion(fullPath,list)+1;

        file.setVersion(version);
        String vPath = pathFinder.getVersionedPath(version,fullPath,file.getType());
        binaryDao.saveBinaryFile(file.getFile(), vPath);
        databaseDao.closeDatabase();

        databaseDao.openDatabase(META_DATABASE_NAME);
        documentDao.addDocument(file.getMeta(), pathFinder.getVersionedPath(version,fullPath,file.getType())+META_FILE_SUFFIX);
        for (MetaFileType type : file.getMetaFiles().keySet()) {
            documentDao.addDocument(file.getMetaFiles().get(type), pathFinder.getVersionedPath(version,fullPath,file.getType())+type.name());
        }
        databaseDao.closeDatabase();


    }

    @Override
    public void deleteFile(String fullPath) throws IOException {
        databaseDao.openDatabase(FILE_DATABASE_NAME);
        String list = databaseDao.listDirectory(FILE_DATABASE_NAME, fullPath.substring(0,fullPath.lastIndexOf("/")));
        int version = pathFinder.getLastVersion(fullPath,list);
        databaseDao.closeDatabase();
        deleteFile(fullPath, version);
    }

    @Override
    public void deleteFile(String fullPath, int version) throws IOException {
        if(version == 0) {
            throw new IOException("Not existing version");
        }
        FileBase f =  getFileInstance(fullPath,version);
        String deletePath = pathFinder.getVersionedPath(version, fullPath, f.getType());
        databaseDao.openDatabase(FILE_DATABASE_NAME);
        databaseDao.deleteFileOrDirectory(deletePath);
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
        databaseDao.openDatabase(FILE_DATABASE_NAME);
        String list = databaseDao.listDirectory(FILE_DATABASE_NAME, fullPath.substring(0,fullPath.lastIndexOf("/")));
        int version = pathFinder.getLastVersion(fullPath,list);
        databaseDao.closeDatabase();
        return getFileByFullPathAndVersion(fullPath, version);
    }

    @Override
    public byte[] getFileByFullPathAndVersion(String fullPath, int version) throws IOException {
        FileBase f =  getFileInstance(fullPath,version);
        String path = pathFinder.getVersionedPath(version, fullPath, f.getType());
        databaseDao.openDatabase(FILE_DATABASE_NAME);
        byte[] output = binaryDao.retrieveBinaryFile(path);
        databaseDao.closeDatabase();
        return output;
    }

    @Override
    public List<Integer> getFileVersions(String fullPath) throws IOException {
        databaseDao.openDatabase(FILE_DATABASE_NAME);
        String list = databaseDao.listDirectory(FILE_DATABASE_NAME, fullPath.substring(0,fullPath.lastIndexOf("/")));
        databaseDao.closeDatabase();
       return pathFinder.getAllVersions(fullPath, list);
    }

    @Override
    public List<PathVersionPair> getAllFilesByFileType(FileType fileType, String namespace) throws IOException {
        databaseDao.openDatabase(FILE_DATABASE_NAME);
        String list = databaseDao.listDirectory(FILE_DATABASE_NAME, namespace);
        databaseDao.closeDatabase();
        return pathFinder.getAllFilesByFileType(fileType,list);
    }

    private FileBase getFileInstance(String fullPath, int version) throws IOException {
        if (fullPath.endsWith(".war")) {
            WarFile file = new WarFile();
            file.setNameVersionPair(new PathVersionPair(fullPath, version));
            return file;
        }
        if (fullPath.endsWith(".xsd")) {
            XsdFile file = new XsdFile();
            file.setNameVersionPair(new PathVersionPair(fullPath, version));
            return file;
        }
        if (fullPath.endsWith(".wsdl")) {
            WsdlFile file = new WsdlFile();
            file.setNameVersionPair(new PathVersionPair(fullPath, version));
            return file;
        }
        throw new IOException();
    }


}
