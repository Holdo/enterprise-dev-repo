package cz.muni.fi.pb138.enums;

/**
 * @author gasior
 */
public enum MetaParameterType {
	LISTENER("listener"),
	FILTER("filter"),
	RESPONSE("response"),
	REQUEST("request"),
	OPERATION("operation"),
	SIMPLETYPE("simpletype"),
	COMPLEXTYPE("complextype"),
	ELEMENT("element"),
	ATTRIBUTE("attribute");

	private final String text;

	MetaParameterType(final String text) {
		this.text = text;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return text;
	}
}
