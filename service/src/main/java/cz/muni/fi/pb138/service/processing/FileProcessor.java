/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing;

import cz.muni.fi.pb138.service.processing.entity.FileBase;


/**
 *
 * @author gasior
 */
public interface FileProcessor {
    
    public FileBase processWar(String fullPath, byte[] file);
    public FileBase processXsd(String fullPath, byte[] file);
    public FileBase processWsdl(String fullPath, byte[] file);
    
}
