package cz.muni.fi.pb138.xquery;


public enum XQueryXsd {
    GET_COMPLEX_TYPES(
            "declare variable $metas := //xsdmeta; "+ "<items>{"+
                    "for $meta in $metas \n"+
                    "for $attr in $meta//complexType"
                    + " return <item><type>complexType</type><name>{$attr/text()}</name><fullPath>{$meta/pathVersionPair/fullPath/text()}</fullPath>"+"<version>{$meta/pathVersionPair/version/text()}</version></item>}</items>"),

    GET_SIMPLE_TYPES(
            "declare variable $metas := //xsdmeta; "+ "<items>{"+
                    "for $meta in $metas \n"+
                    "for $attr in $meta//simpleType"
                    + " return <item><type>simpleType</type><name>{$attr/text()}</name><fullPath>{$meta/pathVersionPair/fullPath/text()}</fullPath>"+"<version>{$meta/pathVersionPair/version/text()}</version></item>}</items>"),

    GET_ELEMENTS(
            "declare variable $metas := //xsdmeta; "+ "<items>{"+
                    "for $meta in $metas \n"+
                    "for $attr in $meta//element"
                    + " return <item><type>element</type><name>{$attr/text()}</name><fullPath>{$meta/pathVersionPair/fullPath/text()}</fullPath>"+"<version>{$meta/pathVersionPair/version/text()}</version></item>}</items>"),
    GET_ATTRIBUTES(
            "declare variable $metas := //xsdmeta; "+ "<items>{"+
            "for $meta in $metas \n"+
            "for $attr in $meta//attribute"
            + " return <item><type>attribute</type><name>{$attr/text()}</name><fullPath>{$meta/pathVersionPair/fullPath/text()}</fullPath>"+"<version>{$meta/pathVersionPair/version/text()}</version></item>}</items>"),

    GET_ATTRIBUTES_BY_FILE("declare variable $fullpath as xs:string external; declare variable $version as xs:string external; "+
            "declare variable $metas := //xsdmeta; "+ "<items>{"+
                    "for $meta in $metas \n"+
                    "for $attr in $meta[./pathVersionPair/version/text()=$version and ./pathVersionPair/fullPath/text()=$fullpath]//attribute"
                    + " return <item><type>attribute</type><name>{$attr/text()}</name><fullPath>{$meta/pathVersionPair/fullPath/text()}</fullPath>"+"<version>{$meta/pathVersionPair/version/text()}</version></item>}</items>"),
    GET_ELEMENTS_BY_FILE("declare variable $fullpath as xs:string external; declare variable $version as xs:string external; "+
            "declare variable $metas := //xsdmeta; "+ "<items>{"+
            "for $meta in $metas \n"+
            "for $attr in $meta[./pathVersionPair/fullPath/text() = $fullpath and ./pathVersionPair/version/text() = $version]//element"
            + " return <item><type>element</type><name>{$attr/text()}</name><fullPath>{$meta/pathVersionPair/fullPath/text()}</fullPath>"+"<version>{$meta/pathVersionPair/version/text()}</version></item>}</items>"),
    GET_SIMPLETYPES_BY_FILE("declare variable $fullpath as xs:string external; declare variable $version as xs:string external; "+
            "declare variable $metas := //xsdmeta; "+ "<items>{"+
            "for $meta in $metas \n"+
            "for $attr in $meta[./pathVersionPair/fullPath/text() = $fullpath and ./pathVersionPair/version/text() = $version]//simpleType"
            + " return <item><type>simpleType</type><name>{$attr/text()}</name><fullPath>{$meta/pathVersionPair/fullPath/text()}</fullPath>"+"<version>{$meta/pathVersionPair/version/text()}</version></item>}</items>"),
    GET_COMPLEXTYPES_BY_FILE("declare variable $fullpath as xs:string external; declare variable $version as xs:string external; "+
            "declare variable $metas := //xsdmeta; "+ "<items>{"+
            "for $meta in $metas \n"+
            "for $attr in $meta[./pathVersionPair/fullPath/text() = $fullpath and ./pathVersionPair/version/text() = $version]//complexType"
            + " return <item><type>complexType</type><name>{$attr/text()}</name><fullPath>{$meta/pathVersionPair/fullPath/text()}</fullPath>"+"<version>{$meta/pathVersionPair/version/text()}</version></item>}</items>"),

    GET_FILE_BY_ATTRIBUTE("b"),
    GET_FILE_BY_ELEMENT("b"),
    GET_FILE_BY_SIMPLETYPE("b"),
    GET_FILE_BY_COMPLEXTYPE("b");

    private final String text;

    XQueryXsd(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
