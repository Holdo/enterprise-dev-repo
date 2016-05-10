/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing.entity;

/**
 *
 * @author gasior
 */
public class NameVersionPair {
    
    private String fileName;
    private int version;

    public NameVersionPair(String fileName, int version) {
        this.fileName = fileName;
        this.version = version;
    }
    
    public NameVersionPair(String fileName) {
        this.fileName = fileName;
        this.version = 1;
    }
    public NameVersionPair() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    
    
}
