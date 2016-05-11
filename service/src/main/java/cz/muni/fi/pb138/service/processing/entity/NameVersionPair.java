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
    
    private String fullPath;
    private int version;

    public NameVersionPair(String fullPath, int version) {
        this.fullPath = fullPath;
        this.version = version;
    }

    public NameVersionPair(String fullPath) {
        this.fullPath = fullPath;
        this.version = 1;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    
    
    
}
