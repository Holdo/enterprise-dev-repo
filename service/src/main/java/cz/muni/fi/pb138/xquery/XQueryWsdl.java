package cz.muni.fi.pb138.xquery;

/**
 * @author Peter Hutta
 * @version 1.0  11.5.2016
 */
public enum XQueryWsdl {
    GET_OPERATIONS("declare variable $file as xs:string external;" +
            "for $op in distinct-values(doc($file)//*[name()='operation']/@name)" +
            "return data($op)"),

    GET_OPERATION_REQUEST("declare namespace functx = \"http://www.functx.com\";" +
            "declare function functx:index-of-string-first" +
            "  ( $arg as xs:string? , $substring as xs:string ) as xs:integer? {" +
            "     if (contains($arg, $substring))" +
            "     then string-length(substring-before($arg, $substring))+1" +
            "     else (0)" +
            "  }; (: used to trim possible namespace :)" +
            "declare variable $file as xs:string external;" +
            "declare variable $oper as xs:string external;" +
            "for $rq in doc($file)//*[name()='operation' and @name=$oper]/*[name()='input']/@message" +
            "return fn:substring(data($rq),functx:index-of-string-first(data($rq),':')+1)"),

    GET_OPERATION_RESPONSE("declare namespace functx = \"http://www.functx.com\";" +
            "declare function functx:index-of-string-first" +
            "  ( $arg as xs:string? , $substring as xs:string ) as xs:integer? {" +
            "     if (contains($arg, $substring))" +
            "     then string-length(substring-before($arg, $substring))+1" +
            "     else (0)" +
            "  }; (: used to trim possible namespace :)" +
            "declare variable $file as xs:string external;" +
            "declare variable $oper as xs:string external;" +
            "for $rs in doc($file)//*[name()='operation' and @name=$oper]/*[name()='output']/@message" +
            "return fn:substring(data($rs),functx:index-of-string-first(data($rs),':')+1)");

    private final String text;

    XQueryWsdl(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
