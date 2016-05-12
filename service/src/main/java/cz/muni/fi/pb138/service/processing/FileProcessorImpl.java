/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing;

import cz.muni.fi.pb138.dao.BinaryDao;
import cz.muni.fi.pb138.service.processing.entity.FileBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public FileBase processWar(String fullPath, byte[] file) {
        
        WarExtractor warExtractor = new WarExtractor(file, fullPath);
        FileBase warFile = warExtractor.getWarFile();
        
        return warFile;
    }

    @Override
    public FileBase processXsd(String fullPath, byte[] file) {
        
        XsdExtractor xsdExtractor = new XsdExtractor(file, fullPath);
        FileBase xsdFile = xsdExtractor.getXsdFile();
        
        return xsdFile;
    }

    @Override
    public FileBase processWsdl(String fullPath, byte[] file) {
        
        WsdlExtractor wsdlExtractor = new WsdlExtractor(file, fullPath);
        FileBase wsdlFile = wsdlExtractor.getWsdlFile();
        return wsdlFile;
    }

   

    
    
    
   

   
}
