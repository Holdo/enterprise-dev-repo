/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service;

import cz.muni.fi.pb138.api.FileService;
import cz.muni.fi.pb138.api.FileType;
import cz.muni.fi.pb138.dao.BinaryDao;
import cz.muni.fi.pb138.dao.DatabaseDao;
import cz.muni.fi.pb138.service.processing.FileProcessor;
import cz.muni.fi.pb138.service.processing.entity.FileBase;
import cz.muni.fi.pb138.service.processing.entity.PathVersionPair;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author gasior
 */
public class FileServiceImpl implements FileService {

    @Autowired
    private DatabaseDao databaseDao;
    @Autowired
    private BinaryDao binaryDao;
    @Autowired
    private FileProcessor fileProcessor;

    public final String FILE_DATABASE_NAME = "artifacts";
    public final String META_DATABASE_NAME = "metadata";
    public final String META_FILE_SUFFIX = ".xml";

   
    @Override
    public void saveFile(String fullPath, byte[] fileBytes) throws IOException {
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
          databaseDao.openDatabase(FILE_DATABASE_NAME);
          binaryDao.saveBinaryFile(file.getFile(), file.getFilePath());
          databaseDao.closeDatabase();
          databaseDao.openDatabase(META_DATABASE_NAME);
          binaryDao.saveBinaryFile(file.getMeta(), file.getMetaPath());
          databaseDao.closeDatabase();

    }

    @Override
    public void deleteFile(String fullPath) throws IOException {
          deleteFile(fullPath, getLastVersion(fullPath));
    }

    @Override
    public void deleteFile(String fullPath, int version) throws IOException {
          databaseDao.openDatabase(FILE_DATABASE_NAME);
          databaseDao.deleteFileOrDirectory(getFullPathWithVersion(fullPath, version));
          databaseDao.closeDatabase();
          databaseDao.openDatabase(META_DATABASE_NAME);
          databaseDao.deleteFileOrDirectory(getFullPathWithVersion(fullPath, version)+META_FILE_SUFFIX);
          databaseDao.closeDatabase();
     
    }

    @Override
    public byte[] getFileByFullPath(String fullPath) throws IOException {
          return getFileByFullPathAndVersion(fullPath,getLastVersion(fullPath));
    }

    @Override
    public byte[] getFileByFullPathAndVersion(String fullPath, int version) throws IOException {
        databaseDao.openDatabase(FILE_DATABASE_NAME);
        byte[] output = binaryDao.retrieveBinaryFile(getFullPathWithVersion(fullPath, version));
        databaseDao.closeDatabase();
        return output;
    }
    
    @Override
    public List<Integer> getFileVersions(String fullPath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<PathVersionPair> getAllFilesByFileType(FileType fileType, String namespace) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private int getLastVersion(String fullPath) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private String getFullPathWithVersion(String fullPath, int version) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
