package cz.muni.fi.pb138.service.processing;

import cz.muni.fi.pb138.dao.BinaryDao;
import cz.muni.fi.pb138.entity.FileBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.zip.DataFormatException;

/**
 *
 * Pro WAR archivy se vytáhne web.xml a bude k náhledu. Dále se vyextrahuje seznam listenerů a filtrů.
 * Pro XSD schémata se vyextrahuje seznam typů (simple a complex) a seznam elementů a atributů
 * Pro WSDL dokumenty se vyextrahuje seznam operací spolu s informací o requestu a response zprávách
 * 
 * @author gasior
 */
@Service
public class FileProcessorImpl implements FileProcessor {
    
    @Autowired
    private BinaryDao BinaryDao;
    
    @Override
    public FileBase processWar(String fullPath, byte[] file) throws DataFormatException, ParserConfigurationException, SAXException, IOException {
        
        WarExtractor warExtractor = new WarExtractor(file, fullPath);

        return warExtractor.getWarFile();
    }

    @Override
    public FileBase processXsd(String fullPath, byte[] file) throws ParserConfigurationException, SAXException, IOException {
        
        XsdExtractor xsdExtractor = new XsdExtractor(file, fullPath);

        return xsdExtractor.getXsdFile();
    }

    @Override
    public FileBase processWsdl(String fullPath, byte[] file) throws ParserConfigurationException, SAXException, IOException {
        
        WsdlExtractor wsdlExtractor = new WsdlExtractor(file, fullPath);
        return wsdlExtractor.getWsdlFile();
    }

   

    
    
    
   

   
}
