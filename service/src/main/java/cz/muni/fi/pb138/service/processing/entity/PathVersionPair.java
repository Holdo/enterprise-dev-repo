/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gasior
 */
public class PathVersionPair {
    
    private String fullPath;
    private int version;

    public PathVersionPair(String fullPath, int version) {
        this.fullPath = fullPath;
        this.version = version;
    }

    public PathVersionPair(String fullPath) {
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


    public static List<PathVersionPair> getListFromXml(String rawOutput) {

        List<PathVersionPair> output = new ArrayList<>();

        //TODO
        return output;
    }
}
