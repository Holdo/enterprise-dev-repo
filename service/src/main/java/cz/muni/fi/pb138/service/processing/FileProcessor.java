package cz.muni.fi.pb138.service.processing;

import cz.muni.fi.pb138.entity.FileBase;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.zip.DataFormatException;


/**
 *
 * @author gasior
 */
public interface FileProcessor {
    
    FileBase processWar(String fullPath, byte[] file) throws DataFormatException, ParserConfigurationException, SAXException, IOException;
    FileBase processXsd(String fullPath, byte[] file) throws ParserConfigurationException, SAXException, IOException;
    FileBase processWsdl(String fullPath, byte[] file) throws ParserConfigurationException, SAXException, IOException;
    
}
