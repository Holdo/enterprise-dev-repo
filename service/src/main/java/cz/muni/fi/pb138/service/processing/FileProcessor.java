package cz.muni.fi.pb138.service.processing;

import cz.muni.fi.pb138.entity.FileBase;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.zip.DataFormatException;


/**
 * For WAR archives it is extracted web.xml, list of listeners and filters
 * For WSDL documents it is extracted list of operations with info about request and response messages
 * For XSD schemas it is extracted list of types (simple and complex), elements and attributes
 *
 * @author Ondřej Gasior
 */
public interface FileProcessor {
    
    FileBase processWar(String fullPath, byte[] file) throws DataFormatException, ParserConfigurationException, SAXException, IOException;
    FileBase processXsd(String fullPath, byte[] file) throws ParserConfigurationException, SAXException, IOException;
    FileBase processWsdl(String fullPath, byte[] file) throws ParserConfigurationException, SAXException, IOException;
    
}
