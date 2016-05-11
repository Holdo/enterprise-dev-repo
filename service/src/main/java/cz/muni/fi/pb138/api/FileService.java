/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.api;

import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * CRUD operations with files
 *
 * @author gasior
 */
@Service
public interface FileService {
    
    /**
     * Create - saved as version 1, Update  - saved as current version + 1
     * @param fullPath - fullpath to file (namespace/file.xxx)
     * @param fileBytes - binary representation of file
     * @throws java.io.IOException
     */
    public void saveFile(String fullPath, byte[] fileBytes)  throws IOException ; 
    /**
     * 
     * @param fullPath - fullpath to file (namespace/file.xxx)
     * @throws java.io.IOException
     */
    public void deleteFile(String fullPath) throws IOException ;
    /**
     * 
     * @param fullPath - fullpath to file (namespace/file.xxx)
     * @param version - number higher than 0, specifies version to be deleted
     * @throws java.io.IOException
     */
    public void deleteFile(String fullPath, int version) throws IOException ;
    /**
     * 
     * @param fullPath - fullpath to file (namespace/file.xxx)
     * @return - binary representation of LAST VERSION of required file
     * @throws java.io.IOException
     */
    public byte[] getFileByFullPath(String fullPath)  throws IOException;
    /**
     * 
     * @param fullPath - fullpath to file (namespace/file.xxx)
     * @param version - number higher than 0, specifies version of required file
     * @return - binary representation required of file
     * @throws java.io.IOException
     */
    public byte[] getFileByFullPathAndVersion(String fullPath, int version) throws IOException;
    /**
     * 
     * @param namespace - all files "/" like this, or you can get some subset 
     * @return - all file names including namespace and extension
     * @throws java.io.IOException
     */
    public List<String> getAllFileFullPathsInNamespace(String namespace) throws IOException;
    /**
     * 
     * @param fullPath - fullpath to file (namespace/file.xxx)
     * @return - versions of fullpath specified file
     * @throws java.io.IOException
     */
    public List<Integer> getFileVersions(String fullPath) throws IOException; 
}
