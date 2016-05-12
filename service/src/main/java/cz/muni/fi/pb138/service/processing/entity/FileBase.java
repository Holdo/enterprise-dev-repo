/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing.entity;

import cz.muni.fi.pb138.api.MetaFileType;

import java.util.HashMap;

/**
 *
 * @author gasior
 */
public interface FileBase {
    
    byte[] getFile();
    byte[] getMeta();
    HashMap<MetaFileType,byte[]> getMetaFiles();
    String getMetaFilePath(MetaFileType type);
    String getFilePath();
    String getMetaPath();
}
