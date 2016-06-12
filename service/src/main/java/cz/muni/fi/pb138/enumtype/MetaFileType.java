package cz.muni.fi.pb138.enumtype;

/**
 *
 * @author gasior
 */
public enum MetaFileType {
    WEBXML("web.xml");
    private final String text;

    /**
     * @param text
     */
    private MetaFileType(final String text) {
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
