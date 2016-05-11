package cz.muni.fi.pb138.service.enums;

/**
 * @author Peter Hutta
 * @version 1.0  11.5.2016
 */
public enum XQueryWar {
    GET_LISTENERS("declare variable $file external; (: path to web.xml :)\n" +
            "for $ls in doc($file)/*[name()='web-app']/*[name()='listener']/*[name()='listener-class']\n" +
            "return data($ls)"),

    GET_FILTERS("declare variable $file external; (: path to web.xml :)\n" +
            "for $fl in doc($file)/*[name()='web-app']/*[name()='filter']/*[name()='filter-class']\n" +
            "return data($fl)");

    private final String text;

    XQueryWar(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
