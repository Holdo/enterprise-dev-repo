/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing;

import cz.muni.fi.pb138.service.processing.entity.WarFile;
import cz.muni.fi.pb138.service.processing.entity.WsdlFile;
import cz.muni.fi.pb138.service.processing.entity.XsdFile;

/**
 *
 * Pro WAR archivy se vytáhne web.xml a bude k náhledu. Dále se vyextrahuje seznam listenerů a filtrů.
 * Pro XSD schémata se vyextrahuje seznam typů (simple a complex) a seznam elementů a atributů
 * Pro WSDL dokumenty se vyextrahuje seznam operací spolu s informací o requestu a response zprávách
 * 
 * @author gasior
 */
public class FileProcessorImpl implements FileProcessor {

    @Override
    public WarFile ProcessWar(String fileName, byte[] file) {
        
        WarExtractor warExtractor = new WarExtractor();
        
        WarFile warFile = new WarFile();
        warFile.setFileName(fileName);
        warFile.setFileBytes(file);
        warFile.setFilterList(warExtractor.ExractFilters(file));
        warFile.setListenerList(warExtractor.ExtractListeners(file));
        warFile.setWebXmlFile(warExtractor.ExtraxtWebXml(file));
        
        return warFile;
    }

    @Override
    public XsdFile ProcessXsd(String fileName, String file) {
        
        XsdExtractor xsdExtractor = new XsdExtractor();
        
        XsdFile xsdFile = new XsdFile();
        xsdFile.setFileName(fileName);
        xsdFile.setFileText(file);
        xsdFile.setSimpleTypes(xsdExtractor.ExtractSimpleTypes(file));
        xsdFile.setComplexTypes(xsdExtractor.ExtractComplexTypes(file));
        xsdFile.setElementAttributesMap(xsdExtractor.ExtractElementsAndAttributes(file));
        
        return xsdFile;
    }

    @Override
    public WsdlFile ProcessWsdl(String fileName, String file) {
        
        WsdlExtractor wsdlExtractor = new WsdlExtractor();
        
        WsdlFile wsdlFile = new WsdlFile();
        wsdlFile.setFileName(fileName);
        wsdlFile.setFileText(file);
        wsdlFile.setOperations(wsdlExtractor.ExtractOperations(file));
        wsdlFile.setOperationRequestMap(wsdlExtractor.ExtractRequests(file,wsdlFile.getOperations()));
        wsdlFile.setOperationResponseMap(wsdlExtractor.ExtractResponses(file,wsdlFile.getOperations()));
        
        return wsdlFile;
    }

   

    
    
    
   

   
}
