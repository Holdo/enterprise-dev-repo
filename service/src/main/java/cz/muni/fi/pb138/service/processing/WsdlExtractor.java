/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing;

import cz.muni.fi.pb138.service.processing.entity.WsdlFile;
import cz.muni.fi.pb138.service.processing.entity.XsdFile;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author gasior
 */
public class WsdlExtractor {
    
    
    private final WsdlFile wsdlFile;
    private final byte[] file;
    private final String fullPath;
    
    public WsdlExtractor(byte[] file, String fullPath) {
       this.file = file;
       this.fullPath = fullPath;
       wsdlFile = new WsdlFile();
       wsdlFile.setFullPath(fullPath);
       wsdlFile.setFile(file);
       extractOperations();
       extractRequests();
       extractResponses();
    }


    public WsdlFile getWsdlFile() {
        return wsdlFile;
    }

    private void extractOperations() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void extractRequests() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void extractResponses() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
