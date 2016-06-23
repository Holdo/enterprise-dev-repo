package cz.muni.fi.pb138.service.processing;

import cz.muni.fi.pb138.entity.metadata.VersionedFile;
import cz.muni.fi.pb138.entity.xsd.XsdFile;
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
 * @author Ondřej Gasior
 */
public class XsdExtractor {

    private final XsdFile xsdFile;
    private final byte[] file;
    private final String fullPath;

    public XsdExtractor(byte[] file, String fullPath) throws IOException, SAXException, ParserConfigurationException {
        this.file = file;
        this.fullPath = fullPath;

        xsdFile = new XsdFile();
        xsdFile.setFile(file);
        xsdFile.setPathVersionPair(new VersionedFile(fullPath, 1, false));
        xsdFile.setSimpleTypes(extract("simpleType"));
        xsdFile.setElements(extract("element"));
        xsdFile.setAttributes(extract("attribute"));
        xsdFile.setComplexTypes(extract("complexType"));
    }

    public XsdFile getXsdFile() {
        return xsdFile;
    }
    
    private List<String> extract(String extractedName) throws ParserConfigurationException, IOException, SAXException {
        List<String> extracted = new ArrayList<>();
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilderFactory.setNamespaceAware(true);
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc;

            doc = docBuilder.parse(new ByteArrayInputStream(file));

            NodeList list = doc.getDocumentElement().getElementsByTagNameNS("*",extractedName);

        for (int i = 0; i < list.getLength(); i++) {
                Element element = (Element) list.item(i);
                if (element.hasAttribute("name")) {
                    extracted.add(element.getAttribute("name"));
                }
        }

        return new ArrayList(new HashSet(extracted));
    }

}
