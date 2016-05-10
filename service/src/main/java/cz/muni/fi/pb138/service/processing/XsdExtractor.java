/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing;

import cz.muni.fi.pb138.service.processing.entity.NameVersionPair;
import cz.muni.fi.pb138.service.processing.entity.XsdFile;
import cz.muni.fi.pb138.service.processing.entity.xsd.ComplexType;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import cz.muni.fi.pb138.service.processing.entity.xsd.*;

/**
 *
 * @author gasior
 */
public class XsdExtractor {

    private final XsdFile xsdFile;
    private final String file;
    private final String fileName;

    public XsdExtractor(String file, String fileName) {
        this.file = file;
        this.fileName = fileName;

        xsdFile = new XsdFile();
        xsdFile.setNameVersionPair(new NameVersionPair(fileName));
        xsdFile.setFileText(file);
        ExtractSimpleTypes();
        ExtractElements();
        ExtractAttributes();
        ExtractComplexTypes();
    }
    public XsdFile GetXsdFile() {
        return xsdFile;
    }
    private void ExtractSimpleTypes() {
        List<String> simpleTypes = new ArrayList<>();
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc;

            doc = docBuilder.parse(new ByteArrayInputStream(file.getBytes(Charset.defaultCharset())));

            NodeList list = doc.getElementsByTagNameNS("*", "simpleType");

            for (int i = 0; i < list.getLength(); i++) {
                Element element = (Element) list.item(i);
                if (element.hasAttribute("name")) {
                    simpleTypes.add(element.getAttribute("name"));
                }
            }
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            System.err.println(ex.getMessage() + " : Simple type extraction failed for : " + fileName);
        }
        xsdFile.setSimpleTypes(simpleTypes);
    }

    private void ExtractComplexTypes() {

        List<String> complexTypes = new ArrayList<>();
        HashMap<String, List<ComplexTypeElement>> typeElementsMap = new HashMap<>();
        HashMap<String, List<ComplexTypeAttribute>> typeAttributesMap = new HashMap<>();

        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc;

            doc = docBuilder.parse(new ByteArrayInputStream(file.getBytes(Charset.defaultCharset())));

            NodeList list = doc.getElementsByTagNameNS("*", "complexType");

            for (int i = 0; i < list.getLength(); i++) {
                Element type = (Element) list.item(i);
                if (type.hasAttribute("name")) {
                    complexTypes.add(type.getAttribute("name"));
                    typeElementsMap.put(type.getAttribute("name"), new ArrayList<>());
                    typeAttributesMap.put(type.getAttribute("name"), new ArrayList<>());

                    NodeList elementList = type.getElementsByTagNameNS("*", "element");
                    for (int j = 0; j < elementList.getLength(); j++) {
                        Element element = (Element) list.item(j);
                        String elementName = "";
                        String elementType = "";
                        if (element.hasAttribute("name")) {
                            elementName = element.getAttribute("name");
                        }
                        if (element.hasAttribute("type")) {
                            elementType = element.getAttribute("type");
                        }
                        typeElementsMap.get(type.getAttribute("name")).add(new ComplexTypeElement(elementName,elementType));
                       
                    }
                    NodeList attributeList = type.getElementsByTagNameNS("*", "attribute");
                    for (int k = 0; k < attributeList.getLength(); k++) {
                        Element attribute = (Element) attributeList.item(k);
                        String attributeName = "";
                        String attributeType = "";
                        if (attribute.hasAttribute("name")) {
                            attributeName = attribute.getAttribute("name");
                        }
                        if (attribute.hasAttribute("type")) {
                            attributeType = attribute.getAttribute("type");
                        }
                       typeAttributesMap.get(type.getAttribute("name")).add(new ComplexTypeAttribute(attributeName,attributeType));
                       
                    }
                }

            }
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            System.err.println(ex.getMessage() + " : Complex type extraction failed for : " + fileName);
        }
        List<ComplexType> types = new ArrayList<>();
        complexTypes.stream().forEach((complexType) -> {
            types.add(new ComplexType(complexType, typeElementsMap.get(complexType), typeAttributesMap.get(complexType)));
        });
          
        xsdFile.setComplexTypes(types);
     
    }

  

    private void ExtractElements() {
        List<String> elements = new ArrayList<>();
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc;

            doc = docBuilder.parse(new ByteArrayInputStream(file.getBytes(Charset.defaultCharset())));

            NodeList list = doc.getElementsByTagNameNS("*", "element");

            for (int i = 0; i < list.getLength(); i++) {
                Element element = (Element) list.item(i);
                if (element.hasAttribute("name")) {
                    elements.add(element.getAttribute("name"));
                }
            }
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            System.err.println(ex.getMessage() + " : Element extraction failed for : " + fileName);
        }
        xsdFile.setElements(elements);
    }

    private void ExtractAttributes() {
        
    List<String> attributes = new ArrayList<>();
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc;

            doc = docBuilder.parse(new ByteArrayInputStream(file.getBytes(Charset.defaultCharset())));

            NodeList list = doc.getElementsByTagNameNS("*", "attribute");

            for (int i = 0; i < list.getLength(); i++) {
                Element element = (Element) list.item(i);
                if (element.hasAttribute("name")) {
                    attributes.add(element.getAttribute("name"));
                }
            }
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            System.err.println(ex.getMessage() + " : Attribute extraction failed for : " + fileName);
        }
        xsdFile.setElements(attributes);
    }

}
