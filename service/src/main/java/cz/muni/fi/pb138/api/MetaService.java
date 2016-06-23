package cz.muni.fi.pb138.api;

import cz.muni.fi.pb138.entity.metadata.Items;
import cz.muni.fi.pb138.entity.metadata.Metas;
import cz.muni.fi.pb138.enums.FileType;
import cz.muni.fi.pb138.enums.MetaFileType;
import cz.muni.fi.pb138.enums.MetaParameterType;
import cz.muni.fi.pb138.entity.metadata.MetaFilePathVersionTriplet;
import cz.muni.fi.pb138.entity.metadata.VersionedFile;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

/**
 * Retrieve operations of meta files
 *
 * @author Ondřej Gasior
 */
public interface MetaService {
   
     /**
<<<<<<< HEAD
     * 
     * @param fileType One of supported file types like WAR, XSD, WSDL
     * @param parameterType One of parameter types like COMPLEXTYPE, OPERATION ...
     * @param parameterName Only results having parameter with this name
     * @return files as PathVersionPair
=======
     * Lists files by parameter type and name
     * @param fileType - One of supported file types like WAR, XSD, WSDL
     * @param parameterType - One of parameter types like COMPLEXTYPE, OPERATION ...
     * @param parameterName - Only results having parameter with this name
     * @return 
>>>>>>> refs/remotes/origin/master
     * @throws java.io.IOException 
     */
     List<VersionedFile> getFilesFullPathsByMetaParameter(FileType fileType, MetaParameterType parameterType, String parameterName, boolean withExactMatch) throws IOException, JAXBException;

    /**
<<<<<<< HEAD
     * 
     * @param parameterType One of parameter types like COMPLEXTYPE, OPERATION ...
     * @return meta parameters of specified type
=======
     * Lists meta parametes by specified type
     * @param parameterType -  One of parameter types like COMPLEXTYPE, OPERATION ...
     * @return 
>>>>>>> refs/remotes/origin/master
     * @throws java.io.IOException 
     */
    List<Items> getAllMetaParametersByMetaParameterType(MetaParameterType parameterType) throws IOException, JAXBException;

    /**
<<<<<<< HEAD
     * 
     * @param metaFileType One of meta file types like WEBXML
     * @param namespace "/" for ALL, specify namaspace to filter
     * @return meta files represented as triplets of file, path and version
=======
     * Returns meta files
     * @param metaFileType - One of meta file types like WEBXML, god knows what else in future...
     * @param namespace - "/" for ALL, specify namaspace to filter
     * @return 
>>>>>>> refs/remotes/origin/master
     * @throws java.io.IOException 
     */
    List<MetaFilePathVersionTriplet> getAllMetaFilesByMetaFileType(MetaFileType metaFileType, String namespace) throws IOException;

    /**
<<<<<<< HEAD
     *
     * @param metaFileType One of meta file types like WEBXML
     * @param fullPath path to file
     * @return meta file represented as triplet of file, path and version
=======
     * Returns meta file
     * @param metaFileType
     * @param fullPath
     * @return
>>>>>>> refs/remotes/origin/master
     * @throws IOException
     */
    MetaFilePathVersionTriplet getMetaFileByFileFullPath(MetaFileType metaFileType, String fullPath) throws IOException;

    /**
<<<<<<< HEAD
     * 
     * @param metaFileType One of meta file types like WEBXML
     * @param fullPath path to file, namespace/foo.war will target namespace/foo.war.web.xml
     * @param version number higher than 0, specifies version of required file
     * @return meta file represented as triplet of file, path and version
=======
     * Returns meta file of specified version
     * @param metaFileType - One of meta file types like WEBXML, god knows what else in future...
     * @param fullPath - path to file, namespace/foo.war will target namespace/foo.war.web.xml
     * @param version
     * @return
>>>>>>> refs/remotes/origin/master
     * @throws IOException 
     */
    MetaFilePathVersionTriplet getMetaFileByFileFullPathAndVersion(MetaFileType metaFileType, String fullPath, int version) throws IOException;
    
    /**
<<<<<<< HEAD
     *
     * @param fullPath path to file
     * @return for example fullpath namespace/foo.xsd, version 2, parameter type COMPLEXTYPE will return all complex types of foo.xsd in version 2
=======
     * Returns meta info from specified file
     * @param fullPath - path to file
     * @return - for example fullpath namespace/foo.xsd, version 2, parameter type COMPLEXTYPE will return all complex types of foo.xsd in version 2
>>>>>>> refs/remotes/origin/master
     * @throws IOException 
     */
    Metas getMetaParametersByFileFullPath(String fullPath) throws IOException, JAXBException;

    /**
<<<<<<< HEAD
     *
     * @param fullPath path to file
     * @param version number higher than 0, specifies version of required file
     * @return for example fullpath namespace/foo.xsd, version 2, parameter type COMPLEXTYPE will return all complex types of foo.xsd in version 2
=======
     * Returns meta info from specified file and version
     * @param fullPath - path to file
     * @param version
     * @return - for example fullpath namespace/foo.xsd, version 2, parameter type COMPLEXTYPE will return all complex types of foo.xsd in version 2
>>>>>>> refs/remotes/origin/master
     * @throws IOException
     */
    Metas getMetaParametersByFileFullPathAndVersion(String fullPath, int version) throws IOException, JAXBException;


}
