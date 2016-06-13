package cz.muni.fi.pb138.service.processing.entity;

import cz.muni.fi.pb138.enums.FileType;
import cz.muni.fi.pb138.enums.MetaFileType;
import cz.muni.fi.pb138.service.processing.entity.xsd.XsdMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * Pro XSD schémata se vyextrahuje seznam typů (simple a complex) a seznam
 * elementů a atributů
 *
 * @author gasior
 */
public class XsdFile implements FileBase {

	private static final Logger log = LoggerFactory.getLogger(XsdFile.class);
	private PathVersionPair nameVersionPair;
	private byte[] file;
	private List<String> elements;
	private List<String> attributes;
	private List<String> simpleTypes;
	private List<String> complexTypes;
	private FileType type;

	public XsdFile() {
		type = FileType.XSD;
	}

	public PathVersionPair getNameVersionPair() {
		return nameVersionPair;
	}

	public void setNameVersionPair(PathVersionPair nameVersionPair) {
		this.nameVersionPair = nameVersionPair;
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
		marshaller.marshal(new XsdMeta(nameVersionPair, elements, attributes, simpleTypes, complexTypes), sw);
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
		nameVersionPair.setVersion(version);
	}

}
