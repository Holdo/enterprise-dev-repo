package cz.muni.fi.pb138.service.xquery;

/**
 * @author Peter Hutta
 * @version 1.0  11.5.2016
 */
public enum XQueryXsd {
    GET_COMPLEX_TYPES("declare variable $file as xs:string external;" +
            "for $tname in distinct-values(doc($file)//*[name()='complexType']/../@name)" +
            "return data($tname)"),

    GET_SIMPLE_TYPES("declare variable $file as xs:string external;" +
            "for $tname in distinct-values(doc($file)//*[name()='simpleType']/../@name)" +
            "return data($tname)"),

    GET_ELEMENTS("declare variable $file as xs:string external;" +
            "for $elem in distinct-values(doc($file)//*[name()='element']/@name)" +
            "return data($elem)"),

    GET_ATTRIBUTES("declare variable $file as xs:string external;" +
            "for $attr in distinct-values(doc($file)//*[name()='attribute']/@name)" +
            "return data($attr)");

    private final String text;

    XQueryXsd(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
