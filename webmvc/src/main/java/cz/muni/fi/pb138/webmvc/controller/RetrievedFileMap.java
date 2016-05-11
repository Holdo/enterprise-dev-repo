/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.webmvc.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

/**
 *
 * @author root
 */
@Component
public class RetrievedFileMap {
    private Map<String,RetrievedFile> myMap = new ConcurrentHashMap();

    public Map<String, RetrievedFile> getMyMap() {
        return myMap;
    }

}



