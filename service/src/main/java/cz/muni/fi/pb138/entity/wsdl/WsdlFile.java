package cz.muni.fi.pb138.entity.wsdl;

import cz.muni.fi.pb138.entity.FileBase;
import cz.muni.fi.pb138.entity.metadata.VersionedFile;
import cz.muni.fi.pb138.entity.metadata.wsdlfield.Request;
import cz.muni.fi.pb138.entity.metadata.wsdlfield.Response;
import cz.muni.fi.pb138.enums.FileType;
import cz.muni.fi.pb138.enums.MetaFileType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

/**
 * For WSDL documents it is extracted list of operations with info about request and response messages
 *
 * @author Ondřej Gasior
 */
public class WsdlFile implements FileBase {


	private VersionedFile pathVersionPair;
	private byte[] file;
	private List<String> operations;
	private List<Response> responses;
	private List<Request> requests;
	private FileType type;

	public WsdlFile() {
		type = FileType.WSDL;
	}


	public VersionedFile getPathVersionPair() {
		return pathVersionPair;
	}

	public void setPathVersionPair(VersionedFile nameVersionPair) {
		this.pathVersionPair = nameVersionPair;
	}


	public void setFile(byte[] file) {
		this.file = file;
	}

	public List<String> getOperations() {
		return operations;
	}

	public void setOperations(List<String> operations) {
		this.operations = operations;
	}

	public List<Response> getResponses() {
		return responses;
	}

	public void setResponses(List<Response> responses) {
		this.responses = responses;
	}

	public List<Request> getRequests() {
		return requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

	@Override
	public byte[] getMeta() throws JAXBException {
		JAXBContext jc;
		Marshaller marshaller;
		jc = JAXBContext.newInstance(WsdlMeta.class);
		marshaller = jc.createMarshaller();

		StringWriter sw = new StringWriter();
		marshaller.marshal(new WsdlMeta(pathVersionPair, operations, responses, requests), sw);
		String xmlString = sw.toString();
		return xmlString.getBytes(StandardCharsets.UTF_8);
	}

	@Override
	public FileType getType() {
		return type;
	}

	@Override
	public HashMap<MetaFileType, byte[]> getMetaFiles() {
		return new HashMap<>();
	}


	@Override
	public byte[] getFile() {
		return file;
	}

	@Override
	public void setVersion(int version) {
		pathVersionPair.setVersion(version);
	}
}
