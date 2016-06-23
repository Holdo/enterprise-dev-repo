package cz.muni.fi.pb138.entity.metadata;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Ondřej Gasior
 */
public class SearchFile {

	private String fullPath;
	private String version;

	public String getFullPath() {
		return fullPath;
	}

	@XmlElement(name = "fullPath")
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getVersion() {
		return version;
	}

	@XmlElement(name = "version")
	public void setVersion(String version) {
		this.version = version;
	}

	public SearchFile(String fullPath, String version) {
		this.fullPath = fullPath;
		this.version = version;
	}

	public SearchFile() {
	}
}
