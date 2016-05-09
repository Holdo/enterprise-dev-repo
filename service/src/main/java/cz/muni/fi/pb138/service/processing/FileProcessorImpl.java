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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public XsdFile ProcessXsd(String fileName, String file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WsdlFile ProcessWsdl(String fileName, String file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
}
