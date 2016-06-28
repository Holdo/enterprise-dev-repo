package cz.muni.fi.pb138.service.processing;

import cz.muni.fi.pb138.entity.metadata.VersionedFile;
import cz.muni.fi.pb138.entity.metadata.xsdfield.Attribute;
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
        xsdFile.setSimpleTypes(extractSimpleTypes("simpleType"));
        xsdFile.setComplexTypes(extractComplexTypes());
    }

    private List<String> extractComplexTypes() throws ParserConfigurationException, IOException, SAXException {
        List<String> output = new ArrayList<>();
        List<cz.muni.fi.pb138.entity.metadata.xsdfield.Element> elements = new ArrayList<>();
        List<Attribute> attributes = new ArrayList<>();

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setNamespaceAware(true);
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc;

        doc = docBuilder.parse(new ByteArrayInputStream(file));

        NodeList list = doc.getDocumentElement().getElementsByTagNameNS("*", "complexType");


        for (int i = 0; i < list.getLength(); i++) {
            Element type = (Element) list.item(i);
            if (type.hasAttribute("name")) {
                output.add(type.getAttribute("name"));


                NodeList elementList = type.getElementsByTagNameNS("*", "element");
                for (int j = 0; j < elementList.getLength(); j++) {
                    Element element = (Element) elementList.item(j);
                    String elementName = "";
                    if (element.hasAttribute("name")) {
                        elementName = element.getAttribute("name");
                        elements.add(new cz.muni.fi.pb138.entity.metadata.xsdfield.Element(elementName, type.getAttribute("name")));
                    }


                }
                NodeList attributeList = type.getElementsByTagNameNS("*", "attribute");
                for (int k = 0; k < attributeList.getLength(); k++) {
                    Element attribute = (Element) attributeList.item(k);
                    String attributeName = "";
                    if (attribute.hasAttribute("name")) {
                        attributeName = attribute.getAttribute("name");
                        attributes.add(new Attribute(attributeName, type.getAttribute("name")));
                    }
                }
            }

        }
        list = doc.getDocumentElement().getElementsByTagNameNS("*", "element");
        for (int i = 0; i < list.getLength(); i++) {

            Element element = (Element) list.item(i);
            String parent = element.getParentNode().getNodeName();

            if (parent.contains(":")) {
                parent = parent.split(":")[1];
            }
            if (parent.equals("schema")) {
                String elementName = "";
                if (element.hasAttribute("name")) {
                    elementName = element.getAttribute("name");
                    elements.add(new cz.muni.fi.pb138.entity.metadata.xsdfield.Element(elementName, "-"));
                }
            }
        }


        xsdFile.setAttributes(new ArrayList(new HashSet(attributes)));
        xsdFile.setElements(new ArrayList(new HashSet(elements)));
        return output;

    }


    public XsdFile getXsdFile() {
        return xsdFile;
    }

    private List<String> extractSimpleTypes(String extractedName) throws ParserConfigurationException, IOException, SAXException {
        List<String> extracted = new ArrayList<>();
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setNamespaceAware(true);
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc;

        doc = docBuilder.parse(new ByteArrayInputStream(file));

        NodeList list = doc.getDocumentElement().getElementsByTagNameNS("*", extractedName);

        for (int i = 0; i < list.getLength(); i++) {
            Element element = (Element) list.item(i);
            if (element.hasAttribute("name")) {
                extracted.add(element.getAttribute("name"));
            }
        }

        return new ArrayList(new HashSet(extracted));
    }

}
