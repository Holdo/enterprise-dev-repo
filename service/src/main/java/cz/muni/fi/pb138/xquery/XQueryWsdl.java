package cz.muni.fi.pb138.xquery;


public enum XQueryWsdl {
    GET_OPERATIONS("declare variable $metas := //wsdlmeta; "+ "<items>{"+
            "for $meta in $metas \n"+
            "for $attr in $meta//operation"
            + " return <item><type>operation</type><name>{$attr/text()}</name><fullPath>{$meta/pathVersionPair/fullPath/text()}</fullPath>"+"<version>{$meta/pathVersionPair/version/text()}</version></item>}</items>"),

    GET_REQUESTS("declare variable $metas := //wsdlmeta; "+ "<items>{"+
                         "for $meta in $metas \n"+
                         "for $attr in $meta//request"
                         + " return <item><type>request</type><name>{$attr/text()}</name><fullPath>{$meta/pathVersionPair/fullPath/text()}</fullPath>"+"<version>{$meta/pathVersionPair/version/text()}</version></item>}</items>"),


    GET_RESPONSES("declare variable $metas := //wsdlmeta; "+ "<items>{"+
            "for $meta in $metas \n"+
            "for $attr in $meta//response"
            + " return <item><type>response</type><name>{$attr/text()}</name><fullPath>{$meta/pathVersionPair/fullPath/text()}</fullPath>"+"<version>{$meta/pathVersionPair/version/text()}</version></item>}</items>"),

    GET_OPERATIONS_BY_FILE("declare variable $fullpath as xs:string external; declare variable $version as xs:string external; "+
            "declare variable $metas := //wsdlmeta; "+ "<items>{"+
            "for $meta in $metas \n"+
            "for $attr in $meta[./pathVersionPair/version/text()=$version and ./pathVersionPair/fullPath/text()=$fullpath]//operation"
            + " return <item><type>operation</type><name>{$attr/text()}</name><fullPath>{$meta/pathVersionPair/fullPath/text()}</fullPath>"+"<version>{$meta/pathVersionPair/version/text()}</version></item>}</items>"),
    GET_REQUESTS_BY_FILE("declare variable $fullpath as xs:string external; declare variable $version as xs:string external; "+
            "declare variable $metas := //wsdlmeta; "+ "<items>{"+
            "for $meta in $metas \n"+
            "for $attr in $meta[./pathVersionPair/version/text()=$version and ./pathVersionPair/fullPath/text()=$fullpath]//request"
            + " return <item><type>request</type><name>{$attr/text()}</name><fullPath>{$meta/pathVersionPair/fullPath/text()}</fullPath>"+"<version>{$meta/pathVersionPair/version/text()}</version></item>}</items>"),
    GET_RESPONSES_BY_FILE("declare variable $fullpath as xs:string external; declare variable $version as xs:string external; "+
            "declare variable $metas := //wsdlmeta; "+ "<items>{"+
            "for $meta in $metas \n"+
            "for $attr in $meta[./pathVersionPair/version/text()=$version and ./pathVersionPair/fullPath/text()=$fullpath]//response"
            + " return <item><type>response</type><name>{$attr/text()}</name><fullPath>{$meta/pathVersionPair/fullPath/text()}</fullPath>"+"<version>{$meta/pathVersionPair/version/text()}</version></item>}</items>"),

    GET_FILE_BY_OPERATION("b"),
    GET_FILE_BY_REQUEST("b"),
    GET_FILE_BY_RESPONSE("b");

    private final String text;

    XQueryWsdl(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
