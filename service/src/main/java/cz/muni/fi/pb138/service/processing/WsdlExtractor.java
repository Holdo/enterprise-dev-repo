package cz.muni.fi.pb138.service.processing;

import cz.muni.fi.pb138.entity.metadata.VersionedFile;
import cz.muni.fi.pb138.entity.wsdl.WsdlFile;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
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
    
    public WsdlExtractor(byte[] file, String fullPath) throws IOException, SAXException, ParserConfigurationException {
       this.file = file;
       this.fullPath = fullPath;
       
       wsdlFile = new WsdlFile();
       wsdlFile.setNameVersionPair(new VersionedFile(fullPath));
       wsdlFile.setFile(file);
       wsdlFile.setOperations(extract("operation", "name"));
       wsdlFile.setRequests(extract("input", "message"));
       wsdlFile.setResponses(extract("output", "message"));
       
    }


    public WsdlFile getWsdlFile() {
        return wsdlFile;
    }

     private List<String> extract(String extractedName, String extractedAttribute) throws ParserConfigurationException, IOException, SAXException {
         List<String> extracted = new ArrayList<>();
         DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
         docBuilderFactory.setNamespaceAware(true);
         DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
         Document doc;

         doc = docBuilder.parse(new ByteArrayInputStream(file));

         NodeList list = doc.getDocumentElement().getElementsByTagNameNS("*",extractedName);

         for (int i = 0; i < list.getLength(); i++) {
             Element element = (Element) list.item(i);
             if (element.hasAttribute(extractedAttribute)) {
                 extracted.add(element.getAttribute(extractedAttribute));
             }
         }


         return new ArrayList(new HashSet(extracted));
     }
    
}
