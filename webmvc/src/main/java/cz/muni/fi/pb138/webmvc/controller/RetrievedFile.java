/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.webmvc.controller;

/**
 *
 * @author root
 */
public class RetrievedFile {
    
    private String name;
    private byte[] binaryMessage;

    public String getName() {
        return name;
    }

    public byte[] getBinaryMessage() {
        return binaryMessage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBinaryMessage(byte[] binaryMessage) {
        this.binaryMessage = binaryMessage;
    }
    
    
    
}
