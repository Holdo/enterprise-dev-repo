package cz.muni.fi.pb138.entity.metadata.xsdfield;

/**
 * @author Michal Holič
 */
public class Attribute {

	private String name;
	private String parent;

	public Attribute() {
	}

	public Attribute(String name, String parent) {
		this.name = name;
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Attribute attribute = (Attribute) o;

		if (!name.equals(attribute.name)) return false;
		return parent.equals(attribute.parent);

	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + parent.hashCode();
		return result;
	}
}
