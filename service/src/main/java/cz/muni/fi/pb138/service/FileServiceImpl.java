/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service;

import cz.muni.fi.pb138.api.FileService;
import cz.muni.fi.pb138.dao.BaseXDao;
import cz.muni.fi.pb138.dao.BinaryDao;
import cz.muni.fi.pb138.service.processing.FileProcessor;
import cz.muni.fi.pb138.service.processing.entity.FileBase;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author gasior
 */
public class FileServiceImpl implements FileService {

    @Autowired
    private BaseXDao baseXDao;
    @Autowired
    private BinaryDao binaryDao;
    @Autowired
    private FileProcessor fileProcessor;

    public final String FILE_DATABASE_NAME = "artifacts";
    public final String META_DATABASE_NAME = "metadata";

    public FileServiceImpl() {
        try {
            baseXDao.createDatabase(FILE_DATABASE_NAME);
            baseXDao.createDatabase(META_DATABASE_NAME);
        } catch (IOException ex) {
            Logger.getLogger(FileServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
      //  binaryDao.saveBinaryFile(file.getFile(), file.getFilePath(), FILE_DATABASE_NAME);
      //  binaryDao.saveBinaryFile(file.getMeta(), file.getMetaPath(), META_DATABASE_NAME);

    }

    @Override
    public void deleteFile(String fullPath) throws IOException {
     //   binaryDao.deleteBinaryFile(getArtifactPath(fullPath), FILE_DATABASE_NAME);
      //  binaryDao.deleteBinaryFile(getMetadataPath(fullPath), FILE_DATABASE_NAME);
    }

    @Override
    public void deleteFile(String fullPath, int version) throws IOException {

      //  binaryDao.deleteBinaryFile(getArtifactPath(fullPath), FILE_DATABASE_NAME);
       // binaryDao.deleteBinaryFile(getMetadataPath(fullPath), FILE_DATABASE_NAME);
    }

    @Override
    public byte[] getFileByFullPath(String fullPath) throws IOException {
       // return binaryDao.retrieveBinaryFile(fullPath, FILE_DATABASE_NAME);
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }

    @Override
    public byte[] getFileByFullPathAndVersion(String fullPath, int version) throws IOException {
     //   return binaryDao.retrieveBinaryFile(fullPath, FILE_DATABASE_NAME);
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }

    @Override
    public List<String> getAllFileFullPaths() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Integer> getFileVersions(String fullPath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
