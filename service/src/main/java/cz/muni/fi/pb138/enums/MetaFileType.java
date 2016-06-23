package cz.muni.fi.pb138.enums;

/**
 *
 * @author Ondřej Gasior
 */
public enum MetaFileType {
    WEBXML("web.xml");
    private final String text;

    MetaFileType(final String text) {
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
