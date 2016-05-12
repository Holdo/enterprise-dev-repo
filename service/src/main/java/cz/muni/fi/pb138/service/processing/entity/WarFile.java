/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing.entity;

import java.util.List;

/** 
 * Pro WAR archivy se vytáhne web.xml a bude k náhledu. Dále se vyextrahuje seznam listenerů a filtrů.
 * @author gasior
 */
public class WarFile implements FileBase {
    
    
    
    private PathVersionPair nameVersionPair;
    private byte[] file;
    private String webXmlFile;
    private List<String> listenerList;
    private List<String> filterList;

    public WarFile() {
    }

    public WarFile(PathVersionPair nameVersionPair, byte[] file, String webXmlFile, List<String> listenerList, List<String> filterList) {
        this.nameVersionPair = nameVersionPair;
        this.file = file;
        this.webXmlFile = webXmlFile;
        this.listenerList = listenerList;
        this.filterList = filterList;
    }

    public PathVersionPair getNameVersionPair() {
        return nameVersionPair;
    }

    public void setNameVersionPair(PathVersionPair nameVersionPair) {
        this.nameVersionPair = nameVersionPair;
    }
    
    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getWebXmlFile() {
        return webXmlFile;
    }

    public void setWebXmlFile(String webXmlFile) {
        this.webXmlFile = webXmlFile;
    }

    public List<String> getListenerList() {
        return listenerList;
    }

    public void setListenerList(List<String> listenerList) {
        this.listenerList = listenerList;
    }

    public List<String> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<String> filterList) {
        this.filterList = filterList;
    }

    @Override
    public byte[] getFile() {
        return file;
    }

    @Override
    public byte[] getMeta() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getFilePath() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getMetaPath() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    
    
    
}
