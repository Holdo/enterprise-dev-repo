package cz.muni.fi.pb138.entity.xsd;

import cz.muni.fi.pb138.entity.metadata.xsdfield.Attribute;
import cz.muni.fi.pb138.entity.metadata.xsdfield.Element;
import cz.muni.fi.pb138.enums.FileType;
import cz.muni.fi.pb138.entity.metadata.VersionedFile;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ondřej Gasior
 */
@XmlRootElement( name = "xsdmeta")
public class XsdMeta {
    
    
    private VersionedFile pathVersionPair;
    private List<Element> element;
    private List<Attribute> attribute;
    private List<String> simpleType;
    private List<String> complexType;
    private FileType type;

    public XsdMeta() {
    }

    public XsdMeta(VersionedFile pathVersionPair, List<Element> elements, List<Attribute> attributes, List<String> simpleTypes, List<String> complexTypes) {
        this.pathVersionPair = pathVersionPair;
        this.element = elements;
        this.attribute = attributes;
        this.simpleType = simpleTypes;
        this.complexType = complexTypes;
        type = FileType.XSD;
    }

    public VersionedFile getPathVersionPair() {
        return pathVersionPair;
    }

    public void setPathVersionPair(VersionedFile pathVersionPair) {
        this.pathVersionPair = pathVersionPair;
    }

    public List<Element> getElement() {
        return element;
    }

    public void setElement(List<Element> element) {
        this.element = element;
    }

    public List<String> getComplexType() {
        return complexType;
    }

    public void setComplexType(List<String> complexType) {
        this.complexType = complexType;
    }

    public List<String> getSimpleType() {
        return simpleType;
    }

    public void setSimpleType(List<String> simpleType) {
        this.simpleType = simpleType;
    }

    public List<Attribute> getAttribute() {
        return attribute;
    }

    public void setAttribute(List<Attribute> attribute) {
        this.attribute = attribute;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }
}
