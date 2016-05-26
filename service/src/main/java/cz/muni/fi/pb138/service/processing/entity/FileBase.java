/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing.entity;

import cz.muni.fi.pb138.api.FileType;
import cz.muni.fi.pb138.api.MetaFileType;

import javax.xml.bind.JAXBException;
import java.util.HashMap;

/**
 *
 * @author gasior
 */
public interface FileBase {

    byte[] getFile();

    byte[] getMeta() throws JAXBException;

    FileType getType();

    HashMap<MetaFileType, byte[]> getMetaFiles();

    void setVersion(int version);
}
