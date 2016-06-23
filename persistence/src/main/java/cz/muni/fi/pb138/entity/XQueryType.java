package cz.muni.fi.pb138.entity;

/**
 * Enum representing various XQuery types
 *
 * @author Michal Holič
 */
public enum XQueryType {
	ANY_URI("xs:anyURI"),
	BOOLEAN("xs:boolean"),
	BYTE("xs:byte"),
	DATE("xs:date"),
	DATE_TIME("xs:dateTime"),
	DECIMAL("xs:decimal"),
	DAY_TIME_DURATION("xf:dayTimeDuration"),
	DOUBLE("xs:double"),
	DURATION("xs:duration"),
	FLOAT("xs:float"),
	G_DAY("xs:gDay"),
	G_MONTH_DAY("xs:gMonthDay"),
	G_YEAR("xs:gYear"),
	G_YEAR_MONTH("xs:gYearMonth"),
	INT("xs:int"),
	LONG("xs:long"),
	NAME("xs:Name"),
	Q_NAME("xs:QName"),
	SHORT("xs:short"),
	STRING("xs:string"),
	TIME("xs:time"),
	YEAR_MONTH_DURATION("xf:yearMonthDuration");

	private final String text;

	XQueryType(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
