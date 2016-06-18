package cz.muni.fi.pb138.xquery;

/**
 * @author Peter Hutta
 * @version 1.0  11.5.2016
 */
public enum XQueryWsdl {
    GET_OPERATIONS("declare variable $file as xs:string external;" +
            "for $op in distinct-values(doc($file)//*[name()='operation']/@name)" +
            "return data($op)"),

    GET_REQUESTS("declare variable $file as xs:string external;" +
            "for $op in distinct-values(doc($file)//*[name()='request']/@name)" +
            "return data($op)"),


    GET_RESPONSES("declare variable $file as xs:string external;" +
            "for $op in distinct-values(doc($file)//*[name()='response']/@name)" +
            "return data($op)");


    private final String text;

    XQueryWsdl(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
