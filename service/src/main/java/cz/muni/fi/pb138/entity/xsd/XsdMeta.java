package cz.muni.fi.pb138.entity.xsd;

import cz.muni.fi.pb138.enums.FileType;
import cz.muni.fi.pb138.entity.metadata.VersionedFile;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gasior
 */
@XmlRootElement( name = "xsdmeta")
public class XsdMeta {
    
    
    private VersionedFile pathVersionPair;
    private List<String> element;
    private List<String> attribute;
    private List<String> simpleType;
    private List<String> complexType;
    private FileType type;

    public XsdMeta() {
    }

    public XsdMeta(VersionedFile pathVersionPair, List<String> elements, List<String> attributes, List<String> simpleTypes, List<String> complexTypes) {
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

    public List<String> getElement() {
        return element;
    }

    public void setElement(List<String> element) {
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

    public List<String> getAttribute() {
        return attribute;
    }

    public void setAttribute(List<String> attribute) {
        this.attribute = attribute;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }
}
