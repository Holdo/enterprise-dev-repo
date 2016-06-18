package cz.muni.fi.pb138.enums;

/**
 *
 * @author gasior
 */
public enum FileType {
    WAR(".war"), XSD(".xsd"), WSDL(".wsdl"), WEB_XML(".war.web.xml");

    private final String text;

    FileType(final String text) {
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
