/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing;

import cz.muni.fi.pb138.service.processing.entity.WarFile;

/**
 *
 * @author gasior
 */
public class WarExtractor {

    private final WarFile warFile;
    private final byte[] file;
    private final String fullPath;

    public WarExtractor(byte[] file, String fullPath) {
        this.file = file;
        this.fullPath = fullPath;
        
        warFile = new WarFile();
    }
    
    
    
    
    public WarFile getWarFile() {
        return warFile;
    }
 
   
}
