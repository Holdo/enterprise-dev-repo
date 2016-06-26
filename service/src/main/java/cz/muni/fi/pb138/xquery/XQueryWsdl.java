package cz.muni.fi.pb138.xquery;


public enum XQueryWsdl {
    //Get all types
    GET_OPERATIONS(
            "<items>{\n" +
                    "for $meta in //wsdlmeta\n" +
                    "let $fullPath := $meta/pathVersionPair/fullPath\n" +
                    "let $version := $meta/pathVersionPair/version\n" + "for $att in $meta/operation\n" +
                    "return <item><type>operation</type><name>{$att/text()}</name><fullPath>{$fullPath/text()}</fullPath>\n" +
                    "<version>{max($version)}</version></item>\n" +
                    "}</items>"),
    GET_REQUESTS(
            "<items>{\n" +
                    "for $meta in //wsdlmeta\n" +
                    "let $fullPath := $meta/pathVersionPair/fullPath\n" +
                    "let $version := $meta/pathVersionPair/version\n" +
                    "for $att in $meta/request\n"+
                    "return <item><type>request</type><name>{$att/name/text()}</name><parent>{$att/parent/text()}</parent><fullPath>{$fullPath/text()}</fullPath>\n" +
                    "<version>{max($version)}</version></item>\n" +
                    "}</items>"),
    GET_RESPONSES(
            "<items>{\n" +
                    "for $meta in //wsdlmeta\n" +
                    "let $fullPath := $meta/pathVersionPair/fullPath\n" +
                    "let $version := $meta/pathVersionPair/version\n" +
                    "for $att in $meta/response\n"+
                    "return <item><type>response</type><name>{$att/name/text()}</name><parent>{$att/parent/text()}</parent><fullPath>{$fullPath/text()}</fullPath>\n" +
                    "<version>{max($version)}</version></item>\n" +
                    "}</items>"),
    //Get by file
    GET_METAS_BY_FILE(
            "declare variable $fullPath as xs:string external;\n" +
                    "declare variable $version as xs:string external;\n" +
                    "for $meta in //wsdlmeta\n" +
                    "for $pair in $meta[pathVersionPair/fullPath/text()=$fullPath and pathVersionPair/version/text()=$version]\n" +
                    "return $pair"),
    //Get by type
    GET_FILES_BY_OPERATION(
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
                    "for $meta in //wsdlmeta\n" +
                    "let $fullPath := $meta/pathVersionPair/fullPath\n" +
                    "let $version := $meta/pathVersionPair/version\n" +
                    "where $meta/operation/text()=$name\n" +
                    "order by $fullPath\n" +
                    "group by $fullPath\n" +
                    "return <file><version>{max($version)}</version><fullPath>{$fullPath}</fullPath></file> else \n" +
                    "for $meta in //wsdlmeta\n" +
                    "let $fullPath := $meta/pathVersionPair/fullPath\n" +
                    "let $version := $meta/pathVersionPair/version\n" +
                    "where (functx:any-contains($name,$meta/operation/text()))" +
                    "order by $fullPath\n" +
                    "group by $fullPath\n" +
                    "return <file><version>{max($version)}</version><fullPath>{$fullPath}</fullPath></file> " +
                    "}</files>"),
    GET_FILES_BY_REQUEST(
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
                    "for $meta in //wsdlmeta\n" +
                    "let $fullPath := $meta/pathVersionPair/fullPath\n" +
                    "let $version := $meta/pathVersionPair/version\n" +
                    "where $meta/request/name/text()=$name\n" +
                    "order by $fullPath\n" +
                    "group by $fullPath\n" +
                    "return <file><version>{max($version)}</version><fullPath>{$fullPath}</fullPath></file> else \n" +
                    "for $meta in //wsdlmeta\n" +
                    "let $fullPath := $meta/pathVersionPair/fullPath\n" +
                    "let $version := $meta/pathVersionPair/version\n" +
                    "where (functx:any-contains($name,$meta/request/name/text()))" +
                    "order by $fullPath\n" +
                    "group by $fullPath\n" +
                    "return <file><version>{max($version)}</version><fullPath>{$fullPath}</fullPath></file> " +
                    "}</files>"),
    GET_FILES_BY_RESPONSE(
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
                    "for $meta in //wsdlmeta\n" +
                    "let $fullPath := $meta/pathVersionPair/fullPath\n" +
                    "let $version := $meta/pathVersionPair/version\n" +
                    "where $meta/response/name/text()=$name\n" +
                    "order by $fullPath\n" +
                    "group by $fullPath\n" +
                    "return <file><version>{max($version)}</version><fullPath>{$fullPath}</fullPath></file> else \n" +
                    "for $meta in //wsdlmeta\n" +
                    "let $fullPath := $meta/pathVersionPair/fullPath\n" +
                    "let $version := $meta/pathVersionPair/version\n" +
                    "where (functx:any-contains($name,$meta/response/name/text()))" +
                    "order by $fullPath\n" +
                    "group by $fullPath\n" +
                    "return <file><version>{max($version)}</version><fullPath>{$fullPath}</fullPath></file> " +
                    "}</files>");
    private final String text;

    XQueryWsdl(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
