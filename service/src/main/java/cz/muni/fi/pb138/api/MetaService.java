/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.api;

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
    public List<PathVersionPair> getFilesFullPathsByMetaParameter(FileType fileType, MetaParameterType parameterType, String namespace, String parameterName) throws IOException;
    /**
     * 
     * @param parameterType -  One of parameter types like COMPLEXTYPE, OPERATION ...
     * @param namespace - "/" for ALL, specify namaspace to filter
     * @return 
     * @throws java.io.IOException 
     */
    public List<PathVersionPair> getAllMetaParametersByMetaParameterType(MetaParameterType parameterType, String namespace) throws IOException;   
    /**
     * 
     * @param metaFileType - One of meta file types like WEBXML, god knows what else in future...
     * @param namespace - "/" for ALL, specify namaspace to filter
     * @return 
     * @throws java.io.IOException 
     */
    public List<MetaFilePathVersionTriplet> getAllMetaFilesByMetaFileType(MetaFileType metaFileType, String namespace) throws IOException;
   
    /**
     * 
     * @param metaFileType - One of meta file types like WEBXML, god knows what else in future...
     * @param fullPath - path to file, namespace/foo.war will target namespace/foo.war.web.xml
     * @param version
     * @return
     * @throws IOException 
     */
    public MetaFilePathVersionTriplet getMetaFileByFileFullPath(MetaFileType metaFileType, String fullPath, String version) throws IOException;
    
    /**
     * 
     * @param parameterType -  One of parameter types like COMPLEXTYPE, OPERATION ...
     * @param fullPath - path to file
     * @param version 
     * @return - for example fullpath namespace/foo.xsd, version 2, parameter type COMPLEXTYPE will return all complex types of foo.xsd in version 2
     * @throws IOException 
     */
    public List<PathVersionPair> getMetaParametersByFileFullPath(MetaParameterType parameterType, String fullPath, String version) throws IOException;

}