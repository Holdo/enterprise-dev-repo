/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing;

import cz.muni.fi.pb138.service.processing.entity.NameVersionPair;
import cz.muni.fi.pb138.service.processing.entity.WsdlFile;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author gasior
 */
public class WsdlExtractor {
    
    
    private final WsdlFile wsdlFile;
    private final byte[] file;
    private final String fullPath;
    
    public WsdlExtractor(byte[] file, String fullPath) {
       this.file = file;
       this.fullPath = fullPath;
       
       wsdlFile = new WsdlFile();
       wsdlFile.setNameVersionPair(new NameVersionPair(fullPath));
       wsdlFile.setFile(file);
       wsdlFile.setOperations(extract("operation"));
       wsdlFile.setRequests(extract("request"));
       wsdlFile.setResponses(extract("response"));
       
    }


    public WsdlFile getWsdlFile() {
        return wsdlFile;
    }

     private List<String> extract(String extractedName) {
        List<String> extracted = new ArrayList<>();
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc;

            doc = docBuilder.parse(new ByteArrayInputStream(file));

            NodeList list = doc.getElementsByTagNameNS("*", extractedName);

            for (int i = 0; i < list.getLength(); i++) {
                Element element = (Element) list.item(i);
                if (element.hasAttribute("name")) {
                    extracted.add(element.getAttribute("name"));
                }
            }
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            System.err.println(ex.getMessage() + " : " + extractedName + " extraction failed for : " + fullPath);
        }
        return extracted;
    }

    
    
}
