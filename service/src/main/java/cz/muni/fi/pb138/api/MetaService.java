package cz.muni.fi.pb138.api;

import cz.muni.fi.pb138.entity.metadata.Items;
import cz.muni.fi.pb138.entity.metadata.Metas;
import cz.muni.fi.pb138.enums.FileType;
import cz.muni.fi.pb138.enums.MetaFileType;
import cz.muni.fi.pb138.enums.MetaParameterType;
import cz.muni.fi.pb138.entity.metadata.VersionedMetaFile;
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
	 * Lists files by parameter type and name
	 *
	 * @param fileType      One of supported file types like WAR, XSD, WSDL
	 * @param parameterType One of parameter types like COMPLEXTYPE, OPERATION ...
	 * @param parameterName Only results having parameter with this name
	 * @return files as PathVersionPair
	 * @throws java.io.IOException
	 */
	List<VersionedFile> getFilesFullPathsByMetaParameter(FileType fileType, MetaParameterType parameterType, String parameterName, boolean withExactMatch) throws IOException, JAXBException;

	/**
	 * Lists meta parametes by specified type
	 *
	 * @param parameterType One of parameter types like COMPLEXTYPE, OPERATION ...
	 * @return meta parameters of specified type
	 * @throws java.io.IOException
	 */
	List<Items> getAllMetaParametersByMetaParameterType(MetaParameterType parameterType) throws IOException, JAXBException;

	/**
	 * Returns meta files
	 *
	 * @param metaFileType One of meta file types like WEBXML
	 * @param namespace    "/" for ALL, specify namaspace to filter
	 * @return meta files represented as triplets of file, path and version
	 * @throws java.io.IOException
	 */
	List<VersionedMetaFile> getAllMetaFilesByMetaFileType(MetaFileType metaFileType, String namespace) throws IOException;

	/**
	 * Returns meta file
	 *
	 * @param metaFileType One of meta file types like WEBXML
	 * @param fullPath     path to file
	 * @return meta file represented as triplet of file, path and version
	 * @throws IOException
	 */
	VersionedMetaFile getMetaFileByFileFullPath(MetaFileType metaFileType, String fullPath) throws IOException;

	/**
	 * Returns meta file of specified version
	 *
	 * @param metaFileType One of meta file types like WEBXML
	 * @param fullPath     path to file, namespace/foo.war will target namespace/foo.war.web.xml
	 * @param version      number higher than 0, specifies version of required file
	 * @return meta file represented as triplet of file, path and version
	 * @throws IOException
	 */
	VersionedMetaFile getMetaFileByFileFullPathAndVersion(MetaFileType metaFileType, String fullPath, int version) throws IOException;

	/**
	 * Returns meta info from specified file of the latest version
	 *
	 * @param fullPath path to file
	 * @return for example fullpath namespace/foo.xsd, version 2, parameter type COMPLEXTYPE will return all complex types of foo.xsd in version 2
	 * @throws IOException
	 */
	Metas getMetaParametersByFileFullPath(String fullPath) throws IOException, JAXBException;

	/**
	 * Returns meta info from specified file and version
	 *
	 * @param fullPath path to file
	 * @param version  number higher than 0, specifies version of required file
	 * @return for example fullpath namespace/foo.xsd, version 2, parameter type COMPLEXTYPE will return all complex types of foo.xsd in version 2
	 * @throws IOException
	 */
	Metas getMetaParametersByFileFullPathAndVersion(String fullPath, int version) throws IOException, JAXBException;


}
