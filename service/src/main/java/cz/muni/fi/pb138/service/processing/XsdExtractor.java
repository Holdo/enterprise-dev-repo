/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing;

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
        xsdFile.setFileName(fileName);
        xsdFile.setFileText(file);
        ExtractSimpleTypes();
        ExtractComplexTypes();
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
            System.err.println(ex.getMessage() + " : Simple type extraction failed for : " + file);
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
                        if (element.hasAttribute("name")) {
                            typeElementsMap.get(type.getAttribute("name")).add(new ComplexTypeElement(element.getAttribute("name"),element.getAttribute("type")));
                        }
                    }
                    NodeList attributeList = type.getElementsByTagNameNS("*", "attribute");
                    for (int k = 0; k < attributeList.getLength(); k++) {
                        Element attribute = (Element) attributeList.item(k);
                        if (attribute.hasAttribute("name")) {
                            typeAttributesMap.get(type.getAttribute("name")).add(new ComplexTypeAttribute(attribute.getAttribute("name"),attribute.getAttribute("type")));
                        }
                    }
                }

            }
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            System.err.println(ex.getMessage() + " : Simple type extraction failed for : " + file);
        }
        List<ComplexType> types = new ArrayList<>();
        complexTypes.stream().forEach((complexType) -> {
            types.add(new ComplexType(complexType, typeElementsMap.get(complexType), typeAttributesMap.get(complexType)));
        });
          
        xsdFile.setComplexTypes(types);
     
    }

    XsdFile GetXsdFile() {
        return xsdFile;
    }

}
