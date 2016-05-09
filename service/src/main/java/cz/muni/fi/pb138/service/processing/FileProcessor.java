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
 * @author gasior
 */
public interface FileProcessor {
    
    public WarFile ProcessWar(String fileName, byte[] file);
    public XsdFile ProcessXsd(String fileName, String file);
    public WsdlFile ProcessWsdl(String fileName, String file);
    
}
