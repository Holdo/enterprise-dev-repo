package cz.muni.fi.pb138.service.processing;

import cz.muni.fi.pb138.enumtype.MetaFileType;
import cz.muni.fi.pb138.service.processing.entity.PathVersionPair;
import cz.muni.fi.pb138.service.processing.entity.WarFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 *
 * @author gasior
 */
public class WarExtractor {

    private final WarFile warFile;
    private final byte[] file;
    private final String fullPath;
    private byte[] webxml;
    private final String webxmlLocation = "WEB-INF/web.xml";
    private final String listenerElement = "listener-class";
    private final String filterElement = "filter-class";
    public WarExtractor(byte[] file, String fullPath) throws DataFormatException, IOException, ParserConfigurationException, SAXException {
        this.file = file;
        this.fullPath = fullPath;
        
        warFile = new WarFile();
        warFile.setFile(file);
        warFile.setNameVersionPair(new PathVersionPair(fullPath));
        extractMetaFiles(MetaFileType.WEBXML, webxmlLocation);
        warFile.getMetaFiles().put(MetaFileType.WEBXML, webxml);
        warFile.setFilterList(extract(filterElement));
        warFile.setListenerList(extract(listenerElement));
    }
    private void extractMetaFiles(MetaFileType webxmlType, String location) throws DataFormatException, IOException, ParserConfigurationException, SAXException {
        FileUtils.writeByteArrayToFile(new File("./tmp.war"), file);
        ZipFile zipFile = new ZipFile("./tmp.war");
        Enumeration entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry ze = (ZipEntry) entries.nextElement();
            if(ze.getName().equals(webxmlLocation)) {
                webxml = IOUtils.toByteArray(zipFile.getInputStream(ze));
            }
        }
        zipFile.close();
        Files.delete(Paths.get("./tmp.war"));
    }

    private List<String> extract(String extractedElement) throws ParserConfigurationException, IOException, SAXException {
        List<String> extracted = new ArrayList<>();
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setNamespaceAware(true);
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc;

        doc = docBuilder.parse(new ByteArrayInputStream(webxml));

        NodeList list = doc.getDocumentElement().getElementsByTagNameNS("*",extractedElement);

        for (int i = 0; i < list.getLength(); i++) {
            Element element = (Element) list.item(i);
            extracted.add(element.getTextContent());
        }


        return new ArrayList(new HashSet(extracted));
    }


    public WarFile getWarFile() {
        return warFile;
    }
 
   
}
