package cz.muni.fi.pb138.xquery;


public enum XQueryWar {
	//Get all types
	GET_LISTENERS(
			"<items>{\n" +
			"for $meta in //warmeta\n" +
			"let $fullPath := $meta/pathVersionPair/fullPath\n" +
			"let $version := $meta/pathVersionPair/version\n" +
			"for $name in $meta/listener/text()\n" +
			"order by $fullPath\n" +
			"group by $fullPath, $name\n" +
			"return <item><type>listener</type><name>{$name}</name><fullPath>{$fullPath}</fullPath>\n" +
			"<version>{max($version)}</version></item>\n" +
			"}</items>"),
	GET_FILTERS(
			"<items>{\n" +
			"for $meta in //warmeta\n" +
			"let $fullPath := $meta/pathVersionPair/fullPath\n" +
			"let $version := $meta/pathVersionPair/version\n" +
			"for $name in $meta/filter/text()\n" +
			"order by $fullPath\n" +
			"group by $fullPath, $name\n" +
			"return <item><type>filter</type><name>{$name}</name><fullPath>{$fullPath}</fullPath>\n" +
			"<version>{max($version)}</version></item>\n" +
			"}</items>"),
	//Get by file
	GET_LISTENERS_BY_FILE(
			"declare variable $fullPath as xs:string external;\n" +
			"declare variable $version as xs:string external;\n" +
			"<items>{\n" +
			"for $meta in //warmeta\n" +
			"for $name in $meta[pathVersionPair/version/text()=$version and pathVersionPair/fullPath/text()=$fullPath]/listener/text()\n" +
			"return <item><type>listener</type><name>{$name}</name><fullPath>{$fullPath}</fullPath>\n" +
			"<version>{max($version)}</version></item>\n" +
			"}</items>"),
	GET_FILTERS_BY_FILE(
			"declare variable $fullPath as xs:string external;\n" +
			"declare variable $version as xs:string external;\n" +
			"<items>{\n" +
			"for $meta in //warmeta\n" +
			"for $name in $meta[pathVersionPair/version/text()=$version and pathVersionPair/fullPath/text()=$fullPath]/filter/text()\n" +
			"return <item><type>filter</type><name>{$name}</name><fullPath>{$fullPath}</fullPath>\n" +
			"<version>{max($version)}</version></item>\n" +
			"}</items>"),
	//Get by type
	GET_FILES_BY_LISTENER(
			"declare variable $name as xs:string external;\n" +
			"<files>{\n" +
			"for $meta in //warmeta\n" +
			"let $fullPath := $meta/pathVersionPair/fullPath\n" +
			"let $version := $meta/pathVersionPair/version\n" +
			"where $meta/listener/text()=$name\n" +
			"order by $fullPath\n" +
			"group by $fullPath\n" +
			"return <file><version>{max($version)}</version><fullPath>{$fullPath}</fullPath></file>\n" +
			"}</files>"),
	GET_FILES_BY_FILTER(
			"declare variable $name as xs:string external;\n" +
			"<files>{\n" +
			"for $meta in //warmeta\n" +
			"let $fullPath := $meta/pathVersionPair/fullPath\n" +
			"let $version := $meta/pathVersionPair/version\n" +
			"where $meta/filter/text()=$name\n" +
			"order by $fullPath\n" +
			"group by $fullPath\n" +
			"return <file><version>{max($version)}</version><fullPath>{$fullPath}</fullPath></file>\n" +
			"}</files>");

	private final String text;

	XQueryWar(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
