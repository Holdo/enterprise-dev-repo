package cz.muni.fi.pb138.xquery;


public enum XQueryWar {
    GET_LISTENERS("declare variable $metas := //warmeta; "+ "<items>{"+
            "for $meta in $metas \n"+
            "for $attr in $meta//listener"
            + " return <item><type>listener</type><name>{$attr/text()}</name><fullPath>{$meta/pathVersionPair/fullPath/text()}</fullPath>"+"<version>{$meta/pathVersionPair/version/text()}</version></item>}</items>"),

    GET_FILTERS("declare variable $metas := //warmeta; "+ "<items>{"+
            "for $meta in $metas \n"+
            "for $attr in $meta//filter"
            + " return <item><type>filter</type><name>{$attr/text()}</name><fullPath>{$meta/pathVersionPair/fullPath/text()}</fullPath>"+"<version>{$meta/pathVersionPair/version/text()}</version></item>}</items>"),

    GET_LISTENERS_BY_FILE("declare variable $fullpath as xs:string external; declare variable $version as xs:string external; "+
            "declare variable $metas := //warmeta; "+ "<items>{"+
            "for $meta in $metas \n"+
            "for $attr in $meta[./pathVersionPair/version/text()=$version and ./pathVersionPair/fullPath/text()=$fullpath]//listener"
            + " return <item><type>listener</type><name>{$attr/text()}</name><fullPath>{$meta/pathVersionPair/fullPath/text()}</fullPath>"+"<version>{$meta/pathVersionPair/version/text()}</version></item>}</items>"),
    GET_FILTERS_BY_FILE("declare variable $fullpath as xs:string external; declare variable $version as xs:string external; "+
            "declare variable $metas := //warmeta; "+ "<items>{"+
            "for $meta in $metas \n"+
            "for $attr in $meta[./pathVersionPair/version/text()=$version and ./pathVersionPair/fullPath/text()=$fullpath]//filter"
            + " return <item><type>filter</type><name>{$attr/text()}</name><fullPath>{$meta/pathVersionPair/fullPath/text()}</fullPath>"+"<version>{$meta/pathVersionPair/version/text()}</version></item>}</items>"),

    GET_FILES_BY_LISTENER("declare variable $name as xs:string external; "+
            "declare variable $metas := //warmeta; "+ "<files>{"+
            "for $meta in $metas \n"+
            "for $attr in $meta[./listener/text()=$name]//pathVersionPair"
            + " return <file><version>{$meta/pathVersionPair/version/text()}</version><path>{$meta/pathVersionPair/fullPath/text()}</path></file>"+"}</files>"),
    GET_FILES_BY_FILTER("declare variable $name as xs:string external; "+
            "declare variable $metas := //warmeta; "+ "<files>{"+
            "for $meta in $metas \n"+
            "for $attr in $meta[./filter/text()=$name]//pathVersionPair"
            + " return <file><version>{$meta/pathVersionPair/version/text()}</version><path>{$meta/pathVersionPair/fullPath/text()}</path></file>"+"}</files>");

    private final String text;

    XQueryWar(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
