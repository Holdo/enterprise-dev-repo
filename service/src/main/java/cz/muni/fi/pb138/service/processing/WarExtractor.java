/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing;

import cz.muni.fi.pb138.api.MetaFileType;
import cz.muni.fi.pb138.service.processing.entity.PathVersionPair;
import cz.muni.fi.pb138.service.processing.entity.WarFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/**
 *
 * @author gasior
 */
public class WarExtractor {

    private final WarFile warFile;
    private final byte[] file;
    private final String fullPath;
    private byte[] webxml;
    private final String webxmlLocation = "\\WEB-INF\\";
    private final String listenerElement = "listener-class";
    private final String filterElement = "filter-class";
    public WarExtractor(byte[] file, String fullPath) throws DataFormatException, IOException, ParserConfigurationException, SAXException {
        this.file = file;
        this.fullPath = fullPath;
        
        warFile = new WarFile();
        warFile.setFile(file);
        warFile.setNameVersionPair(new PathVersionPair(fullPath));
        extractMetaFiles(MetaFileType.WEBXML, webxmlLocation);
        warFile.setFilterList(extract(filterElement));
        warFile.setListenerList(extract(listenerElement));
    }
    private void extractMetaFiles(MetaFileType webxml, String location) throws DataFormatException, IOException, ParserConfigurationException, SAXException {
        String warPath = "."+fullPath.substring(fullPath.lastIndexOf("/"));
       /* FileOutputStream fos = new FileOutputStream(warPath);
        fos.write(decompress());
        fos.close();*/
        JarFile jar = new JarFile(warPath);
        Enumeration enumEntries = jar.entries();
        while (enumEntries.hasMoreElements()) {
            File file = (File) enumEntries.nextElement();
            if(file.getName().endsWith(webxml.name())) {
                byte[] data = Files.readAllBytes(file.toPath());
                warFile.getTypeMetaFileMap().put(webxml,data);
                this.webxml = data;
            }
        }
    }

    private List<String> extract(String extractedElement) {
        List<String> output = new ArrayList<>();
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc;

            doc = docBuilder.parse(new ByteArrayInputStream(webxml));

            NodeList list = doc.getElementsByTagNameNS("*", extractedElement);

            for (int i = 0; i < list.getLength(); i++) {
                Element element = (Element) list.item(i);
                output.add(element.getTextContent());
            }
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            System.err.println(ex.getMessage() + " : " + extractedElement + " extraction failed for : " + fullPath);
        }
        return output;
    }

    private byte[] decompress() throws IOException, DataFormatException {
        byte[] compressedData = null;
        Inflater decompressor = new Inflater();
        decompressor.setInput(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(compressedData.length);
        byte[] buf = new byte[1024];
        while (!decompressor.finished()) {
            int count = decompressor.inflate(buf);
            bos.write(buf, 0, count);

        }
        bos.close();
        return bos.toByteArray();
    }

    public WarFile getWarFile() {
        return warFile;
    }
 
   
}
