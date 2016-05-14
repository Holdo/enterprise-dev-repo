/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing.entity;

import cz.muni.fi.pb138.api.FileType;
import cz.muni.fi.pb138.api.MetaFileType;
import cz.muni.fi.pb138.service.processing.entity.war.WarMeta;
import cz.muni.fi.pb138.service.processing.entity.wsdl.WsdlMeta;
import cz.muni.fi.pb138.service.processing.entity.xsd.XsdMeta;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/** 
 * Pro WAR archivy se vytáhne web.xml a bude k náhledu. Dále se vyextrahuje seznam listenerů a filtrů.
 * @author gasior
 */
public class WarFile implements FileBase {

    private byte[] file;
    private HashMap<MetaFileType, byte[]> typeMetaFileMap;
    private PathVersionPair nameVersionPair;
    private List<String> listenerList;
    private List<String> filterList;
    private FileType type;
    public WarFile() {
        typeMetaFileMap = new HashMap<>();
        type = FileType.WAR;
    }



    public PathVersionPair getNameVersionPair() {
        return nameVersionPair;
    }

    public void setNameVersionPair(PathVersionPair nameVersionPair) {
        this.nameVersionPair = nameVersionPair;
    }

    public HashMap<MetaFileType, byte[]> getTypeMetaFileMap() {
        return typeMetaFileMap;
    }

    public void setTypeMetaFileMap(HashMap<MetaFileType, byte[]> typeMetaFileMap) {
        this.typeMetaFileMap = typeMetaFileMap;
    }

    public void setFile(byte[] file) {
        this.file = file;
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
        JAXBContext jc;
        Marshaller marshaller;
        File xml = null;
        try {
            jc = JAXBContext.newInstance(XsdMeta.class);
            marshaller = jc.createMarshaller();
            marshaller.marshal(new WarMeta(nameVersionPair, listenerList, filterList), xml);
        } catch (JAXBException ex) {
            Logger.getLogger(XsdFile.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            return Files.readAllBytes(xml.toPath());
        } catch (IOException ex) {
            return null;
        }
    }

    @Override
    public FileType getType() {
        return type;
    }

    @Override
    public HashMap<MetaFileType, byte[]> getMetaFiles() {
        return typeMetaFileMap;
    }

    @Override
    public void setVersion(int version) {
        nameVersionPair.setVersion(version);
    }


}
