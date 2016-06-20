package cz.muni.fi.pb138.xquery;


public enum XQueryWsdl {
	GET_OPERATIONS("declare variable $metas := //wsdlmeta; " + "<items>{" +
			"for $meta in $metas \n" +
			"for $attr in $meta//operation\n" +
			"return <item><type>operation</type><name>{$attr/text()}</name><fullPath>{$meta/pathVersionPair/fullPath/text()}</fullPath>" + "<version>{$meta/pathVersionPair/version/text()}</version></item>}</items>"),
	GET_REQUESTS("declare variable $metas := //wsdlmeta; " + "<items>{" +
			"for $meta in $metas \n" +
			"for $attr in $meta//request\n" +
			"return <item><type>request</type><name>{$attr/text()}</name><fullPath>{$meta/pathVersionPair/fullPath/text()}</fullPath>" + "<version>{$meta/pathVersionPair/version/text()}</version></item>}</items>"),
	GET_RESPONSES("declare variable $metas := //wsdlmeta; " + "<items>{" +
			"for $meta in $metas \n" +
			"for $attr in $meta//response\n" +
			"return <item><type>response</type><name>{$attr/text()}</name><fullPath>{$meta/pathVersionPair/fullPath/text()}</fullPath>" + "<version>{$meta/pathVersionPair/version/text()}</version></item>}</items>"),
	GET_OPERATIONS_BY_FILE("declare variable $fullpath as xs:string external; declare variable $version as xs:string external; " +
			"declare variable $metas := //wsdlmeta; " + "<items>{" +
			"for $meta in $metas \n" +
			"for $attr in $meta[./pathVersionPair/version/text()=$version and ./pathVersionPair/fullPath/text()=$fullpath]//operation\n" +
			"return <item><type>operation</type><name>{$attr/text()}</name><fullPath>{$meta/pathVersionPair/fullPath/text()}</fullPath>" + "<version>{$meta/pathVersionPair/version/text()}</version></item>}</items>"),
	GET_REQUESTS_BY_FILE("declare variable $fullpath as xs:string external; declare variable $version as xs:string external; " +
			"declare variable $metas := //wsdlmeta; " + "<items>{" +
			"for $meta in $metas \n" +
			"for $attr in $meta[./pathVersionPair/version/text()=$version and ./pathVersionPair/fullPath/text()=$fullpath]//request\n" +
			"return <item><type>request</type><name>{$attr/text()}</name><fullPath>{$meta/pathVersionPair/fullPath/text()}</fullPath>" + "<version>{$meta/pathVersionPair/version/text()}</version></item>}</items>"),
	GET_RESPONSES_BY_FILE("declare variable $fullpath as xs:string external; declare variable $version as xs:string external; " +
			"declare variable $metas := //wsdlmeta; " + "<items>{" +
			"for $meta in $metas \n" +
			"for $attr in $meta[./pathVersionPair/version/text()=$version and ./pathVersionPair/fullPath/text()=$fullpath]//response\n" +
			"return <item><type>response</type><name>{$attr/text()}</name><fullPath>{$meta/pathVersionPair/fullPath/text()}</fullPath>" + "<version>{$meta/pathVersionPair/version/text()}</version></item>}</items>"),
	GET_FILES_BY_OPERATION("declare variable $name as xs:string external; " +
			"declare variable $metas := //wsdlmeta; " + "<files>{" +
			"for $meta in $metas \n" +
			"for $attr in $meta[./operation/text()=$name]//pathVersionPair\n" +
			"return <file><version>{$meta/pathVersionPair/version/text()}</version><path>{$meta/pathVersionPair/fullPath/text()}</path></file>" + "}</files>"),
	GET_FILES_BY_REQUEST("declare variable $name as xs:string external; " +
			"declare variable $metas := //wsdlmeta; " + "<files>{" +
			"for $meta in $metas \n" +
			"for $attr in $meta[./request/text()=$name]//pathVersionPair\n" +
			"return <file><version>{$meta/pathVersionPair/version/text()}</version><path>{$meta/pathVersionPair/fullPath/text()}</path></file>" + "}</files>"),
	GET_FILES_BY_RESPONSE("declare variable $name as xs:string external; " +
			"declare variable $metas := //wsdlmeta; " + "<files>{" +
			"for $meta in $metas \n" +
			"for $attr in $meta[./response/text()=$name]//pathVersionPair\n" +
			"return <file><version>{$meta/pathVersionPair/version/text()}</version><path>{$meta/pathVersionPair/fullPath/text()}</path></file>" + "}</files>");

	private final String text;

	XQueryWsdl(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
