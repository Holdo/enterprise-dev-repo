package cz.muni.fi.pb138.xquery;


public enum XQueryWsdl {
	//Get all types
	GET_OPERATIONS(
			"<items>{\n" +
			"for $meta in //wsdlmeta\n" +
			"let $fullPath := $meta/pathVersionPair/fullPath\n" +
			"let $version := $meta/pathVersionPair/version\n" +
			"for $name in $meta/operation/text()\n" +
			"order by $fullPath\n" +
			"group by $fullPath, $name\n" +
			"return <item><type>operation</type><name>{$name}</name><fullPath>{$fullPath}</fullPath>\n" +
			"<version>{max($version)}</version></item>\n" +
			"}</items>"),
	GET_REQUESTS(
			"<items>{\n" +
			"for $meta in //wsdlmeta\n" +
			"let $fullPath := $meta/pathVersionPair/fullPath\n" +
			"let $version := $meta/pathVersionPair/version\n" +
			"for $name in $meta/request/text()\n" +
			"order by $fullPath\n" +
			"group by $fullPath, $name\n" +
			"return <item><type>request</type><name>{$name}</name><fullPath>{$fullPath}</fullPath>\n" +
			"<version>{max($version)}</version></item>\n" +
			"}</items>"),
	GET_RESPONSES(
			"<items>{\n" +
			"for $meta in //wsdlmeta\n" +
			"let $fullPath := $meta/pathVersionPair/fullPath\n" +
			"let $version := $meta/pathVersionPair/version\n" +
			"for $name in $meta/response/text()\n" +
			"order by $fullPath\n" +
			"group by $fullPath, $name\n" +
			"return <item><type>response</type><name>{$name}</name><fullPath>{$fullPath}</fullPath>\n" +
			"<version>{max($version)}</version></item>\n" +
			"}</items>"),
	//Get by file
	GET_OPERATIONS_BY_FILE(
			"declare variable $fullPath as xs:string external;\n" +
			"declare variable $version as xs:string external;\n" +
			"<items>{\n" +
			"for $meta in //wsdlmeta\n" +
			"for $name in $meta[pathVersionPair/version/text()=$version and pathVersionPair/fullPath/text()=$fullPath]/operation/text()\n" +
			"return <item><type>operation</type><name>{$name}</name><fullPath>{$fullPath}</fullPath>\n" +
			"<version>{max($version)}</version></item>\n" +
			"}</items>"),
	GET_REQUESTS_BY_FILE(
			"declare variable $fullPath as xs:string external;\n" +
			"declare variable $version as xs:string external;\n" +
			"<items>{\n" +
			"for $meta in //wsdlmeta\n" +
			"for $name in $meta[pathVersionPair/version/text()=$version and pathVersionPair/fullPath/text()=$fullPath]/request/text()\n" +
			"return <item><type>request</type><name>{$name}</name><fullPath>{$fullPath}</fullPath>\n" +
			"<version>{max($version)}</version></item>\n" +
			"}</items>"),
	GET_RESPONSES_BY_FILE(
			"declare variable $fullPath as xs:string external;\n" +
			"declare variable $version as xs:string external;\n" +
			"<items>{\n" +
			"for $meta in //wsdlmeta\n" +
			"for $name in $meta[pathVersionPair/version/text()=$version and pathVersionPair/fullPath/text()=$fullPath]/response/text()\n" +
			"return <item><type>response</type><name>{$name}</name><fullPath>{$fullPath}</fullPath>\n" +
			"<version>{max($version)}</version></item>\n" +
			"}</items>"),
	//Get by type
	GET_FILES_BY_OPERATION(
			"declare variable $name as xs:string external;\n" +
			"<files>{\n" +
			"for $meta in //wsdlmeta\n" +
			"let $fullPath := $meta/pathVersionPair/fullPath\n" +
			"let $version := $meta/pathVersionPair/version\n" +
			"where $meta/operation/text()=$name\n" +
			"order by $fullPath\n" +
			"group by $fullPath\n" +
			"return <file><version>{max($version)}</version><fullPath>{$fullPath}</fullPath></file>\n" +
			"}</files>"),
	GET_FILES_BY_REQUEST(
			"declare variable $name as xs:string external;\n" +
			"<files>{\n" +
			"for $meta in //wsdlmeta\n" +
			"let $fullPath := $meta/pathVersionPair/fullPath\n" +
			"let $version := $meta/pathVersionPair/version\n" +
			"where $meta/request/text()=$name\n" +
			"order by $fullPath\n" +
			"group by $fullPath\n" +
			"return <file><version>{max($version)}</version><fullPath>{$fullPath}</fullPath></file>\n" +
			"}</files>"),
	GET_FILES_BY_RESPONSE(
			"declare variable $name as xs:string external;\n" +
			"<files>{\n" +
			"for $meta in //wsdlmeta\n" +
			"let $fullPath := $meta/pathVersionPair/fullPath\n" +
			"let $version := $meta/pathVersionPair/version\n" +
			"where $meta/response/text()=$name\n" +
			"order by $fullPath\n" +
			"group by $fullPath\n" +
			"return <file><version>{max($version)}</version><fullPath>{$fullPath}</fullPath></file>\n" +
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
