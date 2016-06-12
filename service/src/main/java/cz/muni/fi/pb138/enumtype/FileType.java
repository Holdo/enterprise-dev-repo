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
