package cz.muni.fi.pb138.api;

import cz.muni.fi.pb138.enums.FileType;
import cz.muni.fi.pb138.entity.metadata.VersionedFile;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * CRUD operations with files
 *
 * @author gasior
 */
public interface FileService {
    
    /**
     * Create - saved as version 1, Update  - saved as current version + 1
     * @param fullPath - fullpath to file (namespace/file.xxx)
     * @param fileBytes - binary representation of file
     * @throws java.io.IOException
     */
    void saveFile(String fullPath, byte[] fileBytes) throws IOException, SAXException, ParserConfigurationException, DataFormatException, JAXBException;

    /**
     * 
     * @param fullPath - fullpath to file (namespace/file.xxx)
     * @throws java.io.IOException
     */
    void deleteFile(String fullPath) throws IOException ;

    /**
     * 
     * @param fullPath - fullpath to file (namespace/file.xxx)
     * @param version - number higher than 0, specifies version to be deleted
     * @throws java.io.IOException
     */
    void deleteFile(String fullPath, int version) throws IOException ;

    /**
     * 
     * @param fullPath - fullpath to file (namespace/file.xxx)
     * @return - binary representation of LAST VERSION of required file
     * @throws java.io.IOException
     */
    byte[] getFileByFullPath(String fullPath)  throws IOException;

    /**
     * 
     * @param fullPath - fullpath to file (namespace/file.xxx)
     * @param version - number higher than 0, specifies version of required file
     * @return - binary representation required of file
     * @throws java.io.IOException
     */
    byte[] getFileByFullPathAndVersion(String fullPath, int version) throws IOException;

    /**
     *
     * @param namespace - "/" for ALL, specify namaspace to filter
     * @param allVersions - true for all versions, false for just the latest ones
     * @param metaDatabase -  whether to list metaDatabase instead of document database
     * @return files as PathVersionPair
     * @throws java.io.IOException
     */
    List<VersionedFile> listAllFiles(String namespace, boolean allVersions, boolean metaDatabase) throws IOException;

    /**
     * 
     * @param namespace - "/" for ALL, specify namaspace to filter
     * @param allVersions - true for all versions, false for just the latest ones
     * @param metaDatabase -  whether to list metaDatabase instead of document database
     * @param fileType - One of supported file types like WAR, XSD, WSDL
     * @return files as PathVersionPair
     * @throws java.io.IOException 
     */
    List<VersionedFile> listAllFilesByFileType(String namespace, boolean allVersions, boolean metaDatabase, FileType fileType) throws IOException;


    /**
     *
     * @param namespace - "/" for ALL, specify namaspace to filter
     * @param allVersions - true for all versions, false for just the latest ones
     * @param metaDatabase -  whether to list metaDatabase instead of document database
     * @param fileType - One of supported file types like WAR, XSD, WSDL
     * @return files as PathVersionPair
     * @throws java.io.IOException
     */
    List<VersionedFile> listAllFilesByFileTypeRecursively(String namespace, boolean allVersions, boolean metaDatabase, FileType fileType) throws IOException;

    /**
     * 
     * @param fullPath - fullpath to file (namespace/file.xxx)
     * @return - versions of fullpath specified file
     * @throws java.io.IOException
     */
    List<Integer> listFileVersions(String fullPath) throws IOException;
}
