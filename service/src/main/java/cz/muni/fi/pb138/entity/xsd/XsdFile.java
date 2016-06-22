package cz.muni.fi.pb138.entity.xsd;

import cz.muni.fi.pb138.entity.metadata.Items;
import cz.muni.fi.pb138.enums.FileType;
import cz.muni.fi.pb138.enums.MetaFileType;
import cz.muni.fi.pb138.entity.FileBase;
import cz.muni.fi.pb138.entity.metadata.VersionedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * For XSD schemas it is extracted list of types (simple and complex), elements and attributes
 *
 * @author Ondřej Gasior
 */
public class XsdFile implements FileBase {

	private static final Logger log = LoggerFactory.getLogger(XsdFile.class);
	private VersionedFile pathVersionPair;
	private byte[] file;
	private List<String> elements;
	private List<String> attributes;
	private List<String> simpleTypes;
	private List<String> complexTypes;
	private FileType type;

	public XsdFile() {
		type = FileType.XSD;
	}

	public VersionedFile getNameVersionPair() {
		return pathVersionPair;
	}

	public void setPathVersionPair(VersionedFile nameVersionPair) {
		this.pathVersionPair = nameVersionPair;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public List<String> getElements() {
		return elements;
	}

	public void setElements(List<String> elements) {
		this.elements = elements;
	}

	public List<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<String> attributes) {
		this.attributes = attributes;
	}

	public List<String> getSimpleTypes() {
		return simpleTypes;
	}

	public void setSimpleTypes(List<String> simpleTypes) {
		this.simpleTypes = simpleTypes;
	}

	public List<String> getComplexTypes() {
		return complexTypes;
	}

	public void setComplexTypes(List<String> complexTypes) {
		this.complexTypes = complexTypes;
	}

	@Override
	public byte[] getMeta() throws JAXBException {
		JAXBContext jc;
		Marshaller marshaller;
		jc = JAXBContext.newInstance(XsdMeta.class);
		marshaller = jc.createMarshaller();

		StringWriter sw = new StringWriter();
		marshaller.marshal(new XsdMeta(pathVersionPair, elements, attributes, simpleTypes, complexTypes), sw);
		String xmlString = sw.toString();
		log.debug("Extracted metadata XML from XSD: {}", xmlString);



		return xmlString.getBytes(StandardCharsets.UTF_8);
	}

	@Override
	public FileType getType() {
		return type;
	}

	@Override
	public byte[] getFile() {
		return file;
	}

	@Override
	public HashMap<MetaFileType, byte[]> getMetaFiles() {
		return new HashMap<>();
	}

	@Override
	public void setVersion(int version) {
		pathVersionPair.setVersion(version);
	}

}
