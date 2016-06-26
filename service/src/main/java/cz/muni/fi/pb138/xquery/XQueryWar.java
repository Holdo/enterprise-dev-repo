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
	GET_METAS_BY_FILE(
			"declare variable $fullPath as xs:string external;\n" +
			"declare variable $version as xs:string external;\n" +
			"for $meta in //warmeta\n" +
			"for $pair in $meta[pathVersionPair/fullPath/text()=$fullPath and pathVersionPair/version/text()=$version]\n" +
			"return $pair"),
	//Get by type
	GET_FILES_BY_LISTENER(
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
					"for $meta in //warmeta\n" +
					"let $fullPath := $meta/pathVersionPair/fullPath\n" +
					"let $version := $meta/pathVersionPair/version\n" +
					"where $meta/listener/text()=$name\n" +
					"order by $fullPath\n" +
					"group by $fullPath\n" +
					"return <file><version>{max($version)}</version><fullPath>{$fullPath}</fullPath></file> else \n"+
					"for $meta in //warmeta\n" +
					"let $fullPath := $meta/pathVersionPair/fullPath\n" +
					"let $version := $meta/pathVersionPair/version\n" +
					"where (functx:any-contains($name,$meta/listener/text()))"+
					"order by $fullPath\n" +
					"group by $fullPath\n" +
					"return <file><version>{max($version)}</version><fullPath>{$fullPath}</fullPath></file> "+
					"}</files>"),
	GET_FILES_BY_FILTER(
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
					"for $meta in //warmeta\n" +
					"let $fullPath := $meta/pathVersionPair/fullPath\n" +
					"let $version := $meta/pathVersionPair/version\n" +
					"where $meta/filter/text()=$name\n" +
					"order by $fullPath\n" +
					"group by $fullPath\n" +
					"return <file><version>{max($version)}</version><fullPath>{$fullPath}</fullPath></file> else \n"+
					"for $meta in //warmeta\n" +
					"let $fullPath := $meta/pathVersionPair/fullPath\n" +
					"let $version := $meta/pathVersionPair/version\n" +
					"where (functx:any-contains($name,$meta/filter/text()))"+
					"order by $fullPath\n" +
					"group by $fullPath\n" +
					"return <file><version>{max($version)}</version><fullPath>{$fullPath}</fullPath></file> "+
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
