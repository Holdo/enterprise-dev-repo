/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.enumtype;

/**
 *
 * @author gasior
 */
public enum FileType {
    WAR(".war"), XSD(".xsd"), WSDL(".wsdl");
    private final String text;

    /**
     * @param text
     */
    private FileType(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
