package cz.muni.fi.pb138.api;

import cz.muni.fi.pb138.enums.FileType;
import cz.muni.fi.pb138.enums.MetaFileType;
import cz.muni.fi.pb138.enums.MetaParameterType;
import cz.muni.fi.pb138.service.processing.entity.MetaFilePathVersionTriplet;
import cz.muni.fi.pb138.service.processing.entity.PathVersionPair;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author gasior
 */
public interface MetaService {
   
     /**
     * 
     * @param fileType - One of supported file types like WAR, XSD, WSDL
     * @param parameterType - One of parameter types like COMPLEXTYPE, OPERATION ...
     * @param namespace - "/" for ALL, specify namaspace to filter 
     * @param parameterName - Only results having parameter with this name
     * @return 
     * @throws java.io.IOException 
     */
     List<PathVersionPair> getFilesFullPathsByMetaParameter(FileType fileType, MetaParameterType parameterType, String namespace, String parameterName) throws IOException;
    /**
     * 
     * @param parameterType -  One of parameter types like COMPLEXTYPE, OPERATION ...
     * @param namespace - "/" for ALL, specify namaspace to filter
     * @return 
     * @throws java.io.IOException 
     */
    List<PathVersionPair> getAllMetaParametersByMetaParameterType(MetaParameterType parameterType, String namespace) throws IOException;
    /**
     * 
     * @param metaFileType - One of meta file types like WEBXML, god knows what else in future...
     * @param namespace - "/" for ALL, specify namaspace to filter
     * @return 
     * @throws java.io.IOException 
     */
    List<MetaFilePathVersionTriplet> getAllMetaFilesByMetaFileType(MetaFileType metaFileType, String namespace) throws IOException;

    /**
     *
     * @param metaFileType
     * @param fullPath
     * @return
     * @throws IOException
     */
    MetaFilePathVersionTriplet getMetaFileByFileFullPath(MetaFileType metaFileType, String fullPath) throws IOException;
    /**
     * 
     * @param metaFileType - One of meta file types like WEBXML, god knows what else in future...
     * @param fullPath - path to file, namespace/foo.war will target namespace/foo.war.web.xml
     * @param version
     * @return
     * @throws IOException 
     */
    MetaFilePathVersionTriplet getMetaFileByFileFullPathAndVersion(MetaFileType metaFileType, String fullPath, int version) throws IOException;
    
    /**
     * 
     * @param parameterType -  One of parameter types like COMPLEXTYPE, OPERATION ...
     * @param fullPath - path to file
     * @return - for example fullpath namespace/foo.xsd, version 2, parameter type COMPLEXTYPE will return all complex types of foo.xsd in version 2
     * @throws IOException 
     */
    List<PathVersionPair> getMetaParametersByFileFullPath(MetaParameterType parameterType, String fullPath) throws IOException;
    /**
     *
     * @param parameterType -  One of parameter types like COMPLEXTYPE, OPERATION ...
     * @param fullPath - path to file
     * @param version
     * @return - for example fullpath namespace/foo.xsd, version 2, parameter type COMPLEXTYPE will return all complex types of foo.xsd in version 2
     * @throws IOException
     */
    List<PathVersionPair> getMetaParametersByFileFullPathAndVersion(MetaParameterType parameterType, String fullPath, String version) throws IOException;


}
