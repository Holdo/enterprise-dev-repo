package cz.muni.fi.pb138.xquery;


public enum XQueryXsd {
    //Get all types
    GET_COMPLEX_TYPES(
            "<items>{\n" +
                    "for $meta in //xsdmeta\n" +
                    "let $fullPath := $meta/pathVersionPair/fullPath\n" +
                    "let $version := $meta/pathVersionPair/version\n" +
                    "for $name in $meta/complexType/text()\n" +
                    "order by $fullPath\n" +
                    "group by $fullPath, $name\n" +
                    "return <item><type>complexType</type><name>{$name}</name><fullPath>{$fullPath}</fullPath>\n" +
                    "<version>{max($version)}</version></item>\n" +
                    "}</items>"),
    GET_SIMPLE_TYPES(
            "<items>{\n" +
                    "for $meta in //xsdmeta\n" +
                    "let $fullPath := $meta/pathVersionPair/fullPath\n" +
                    "let $version := $meta/pathVersionPair/version\n" +
                    "for $name in $meta/simpleType/text()\n" +
                    "order by $fullPath\n" +
                    "group by $fullPath\n" +
                    "return <item><type>simpleType</type><name>{$name}</name><fullPath>{$fullPath}</fullPath>\n" +
                    "<version>{max($version)}</version></item>\n" +
                    "}</items>"),
    GET_ELEMENTS(
            "<items>{\n" +
                    "for $meta in //xsdmeta\n" +
                    "let $fullPath := $meta/pathVersionPair/fullPath\n" +
                    "let $version := $meta/pathVersionPair/version\n" +
                    "for $name in $meta/element/text()\n" +
                    "order by $fullPath\n" +
                    "group by $fullPath\n" +
                    "return <item><type>element</type><name>{$name}</name><fullPath>{$fullPath}</fullPath>\n" +
                    "<version>{max($version)}</version></item>\n" +
                    "}</items>"),
    GET_ATTRIBUTES(
            "<items>{\n" +
                    "for $meta in //xsdmeta\n" +
                    "let $fullPath := $meta/pathVersionPair/fullPath\n" +
                    "let $version := $meta/pathVersionPair/version\n" +
                    "for $name in $meta/attribute/text()\n" +
                    "order by $fullPath\n" +
                    "group by $fullPath\n" +
                    "return <item><type>attribute</type><name>{$name}</name><fullPath>{$fullPath}</fullPath>\n" +
                    "<version>{max($version)}</version></item>\n" +
                    "}</items>"),
    //Get metas by file
    GET_METAS_BY_FILE(
            "declare variable $fullPath as xs:string external;\n" +
                    "declare variable $version as xs:string external;\n" +
                    "for $meta in //xsdmeta\n" +
                    "for $pair in $meta[pathVersionPair/fullPath/text()=$fullPath and pathVersionPair/version/text()=$version]\n" +
                    "return $pair"),
    //Get by type
    GET_FILES_BY_ATTRIBUTE(
            "declare namespace functx = \"http://www.functx.com\";\n" +
                    "declare function functx:any-contains\n" +
                    "  ( $arg as xs:string? ,\n" +
                    "    $searchStrings as xs:string* )  as xs:boolean {\n" +
                    "\n" +
                    "   some $searchString in $searchStrings\n" +
                    "   satisfies contains($searchString,$arg)\n" +
                    " } ; " +
            "declare variable $name as xs:string external;\n" +
            "declare variable $exactMatch as xs:boolean external;\n" +
                    "<files>{\n" +
                    "if ($exactMatch) then " +
                    "for $meta in //xsdmeta\n" +
                    "let $fullPath := $meta/pathVersionPair/fullPath\n" +
                    "let $version := $meta/pathVersionPair/version\n" +
                    "where $meta/attribute/text()=$name\n" +
                    "order by $fullPath\n" +
                    "group by $fullPath\n" +
                    "return <file><version>{max($version)}</version><fullPath>{$fullPath}</fullPath></file> else \n"+
                    "for $meta in //xsdmeta\n" +
                    "let $fullPath := $meta/pathVersionPair/fullPath\n" +
                    "let $version := $meta/pathVersionPair/version\n" +
                    "where (functx:any-contains($name,$meta/attribute/text()))"+
                    "order by $fullPath\n" +
                    "group by $fullPath\n" +
                    "return <file><version>{max($version)}</version><fullPath>{$fullPath}</fullPath></file> "+
                    "}</files>"),
    GET_FILES_BY_ELEMENT(
            "declare namespace functx = \"http://www.functx.com\";\n" +
                    "declare function functx:any-contains\n" +
                    "  ( $arg as xs:string? ,\n" +
                    "    $searchStrings as xs:string* )  as xs:boolean {\n" +
                    "\n" +
                    "   some $searchString in $searchStrings\n" +
                    "   satisfies contains($searchString,$arg)\n" +
                    " } ; " +
                    "declare variable $name as xs:string external;\n" +
                    "declare variable $exactMatch as xs:boolean external;\n" +
                    "<files>{\n" +
                    "if ($exactMatch) then " +
                    "for $meta in //xsdmeta\n" +
                    "let $fullPath := $meta/pathVersionPair/fullPath\n" +
                    "let $version := $meta/pathVersionPair/version\n" +
                    "where $meta/element/text()=$name\n" +
                    "order by $fullPath\n" +
                    "group by $fullPath\n" +
                    "return <file><version>{max($version)}</version><fullPath>{$fullPath}</fullPath></file> else \n"+
                    "for $meta in //xsdmeta\n" +
                    "let $fullPath := $meta/pathVersionPair/fullPath\n" +
                    "let $version := $meta/pathVersionPair/version\n" +
                    "where (functx:any-contains($name,$meta/element/text()))"+
                    "order by $fullPath\n" +
                    "group by $fullPath\n" +
                    "return <file><version>{max($version)}</version><fullPath>{$fullPath}</fullPath></file> "+
                    "}</files>"),
    GET_FILES_BY_SIMPLETYPE(
            "declare namespace functx = \"http://www.functx.com\";\n" +
                    "declare function functx:any-contains\n" +
                    "  ( $arg as xs:string? ,\n" +
                    "    $searchStrings as xs:string* )  as xs:boolean {\n" +
                    "\n" +
                    "   some $searchString in $searchStrings\n" +
                    "   satisfies contains($searchString,$arg)\n" +
                    " } ; " +
                    "declare variable $name as xs:string external;\n" +
                    "declare variable $exactMatch as xs:boolean external;\n" +
                    "<files>{\n" +
                    "if ($exactMatch) then " +
                    "for $meta in //xsdmeta\n" +
                    "let $fullPath := $meta/pathVersionPair/fullPath\n" +
                    "let $version := $meta/pathVersionPair/version\n" +
                    "where $meta/simpleType/text()=$name\n" +
                    "order by $fullPath\n" +
                    "group by $fullPath\n" +
                    "return <file><version>{max($version)}</version><fullPath>{$fullPath}</fullPath></file> else \n"+
                    "for $meta in //xsdmeta\n" +
                    "let $fullPath := $meta/pathVersionPair/fullPath\n" +
                    "let $version := $meta/pathVersionPair/version\n" +
                    "where (functx:any-contains($name,$meta/simpleType/text()))"+
                    "order by $fullPath\n" +
                    "group by $fullPath\n" +
                    "return <file><version>{max($version)}</version><fullPath>{$fullPath}</fullPath></file> "+
                    "}</files>"),
    GET_FILES_BY_COMPLEXTYPE(
            "declare namespace functx = \"http://www.functx.com\";\n" +
                    "declare function functx:any-contains\n" +
                    "  ( $arg as xs:string? ,\n" +
                    "    $searchStrings as xs:string* )  as xs:boolean {\n" +
                    "\n" +
                    "   some $searchString in $searchStrings\n" +
                    "   satisfies contains($searchString,$arg)\n" +
                    " } ; " +
                    "declare variable $name as xs:string external;\n" +
                    "declare variable $exactMatch as xs:boolean external;\n" +
                    "<files>{\n" +
                    "if ($exactMatch) then " +
                    "for $meta in //xsdmeta\n" +
                    "let $fullPath := $meta/pathVersionPair/fullPath\n" +
                    "let $version := $meta/pathVersionPair/version\n" +
                    "where $meta/complexType/text()=$name\n" +
                    "order by $fullPath\n" +
                    "group by $fullPath\n" +
                    "return <file><version>{max($version)}</version><fullPath>{$fullPath}</fullPath></file> else \n"+
                    "for $meta in //xsdmeta\n" +
                    "let $fullPath := $meta/pathVersionPair/fullPath\n" +
                    "let $version := $meta/pathVersionPair/version\n" +
                    "where (functx:any-contains($name,$meta/complexType/text()))"+
                    "order by $fullPath\n" +
                    "group by $fullPath\n" +
                    "return <file><version>{max($version)}</version><fullPath>{$fullPath}</fullPath></file> "+
                    "}</files>");

    private final String text;

    XQueryXsd(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
