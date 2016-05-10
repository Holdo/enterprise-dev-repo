/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.service.processing.entity.xsd;

import java.util.List;

/**
 *
 * @author gasior
 */
public class ComplexType {
    private final String name;
    private final List<ComplexTypeElement> elements;
    private final List<ComplexTypeAttribute> attributes;

    public ComplexType(String name, List<ComplexTypeElement> elements, List<ComplexTypeAttribute> attributes) {
        this.name = name;
        this.elements = elements;
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public List<ComplexTypeElement> getElements() {
        return elements;
    }

    public List<ComplexTypeAttribute> getAttributes() {
        return attributes;
    }

    
    
}
