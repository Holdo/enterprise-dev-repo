package cz.muni.fi.pb138.service.processing;

import cz.muni.fi.pb138.entity.metadata.Name;
import cz.muni.fi.pb138.entity.metadata.Parent;
import cz.muni.fi.pb138.entity.metadata.VersionedFile;
import cz.muni.fi.pb138.entity.metadata.wsdlfield.Request;
import cz.muni.fi.pb138.entity.metadata.wsdlfield.Response;
import cz.muni.fi.pb138.entity.metadata.xsdfield.Attribute;
import cz.muni.fi.pb138.entity.wsdl.WsdlFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Ondřej Gasior
 */
public class WsdlExtractor {

	private final WsdlFile wsdlFile;
	private final byte[] file;
	private final String fullPath;

	public WsdlExtractor(byte[] file, String fullPath) throws IOException, SAXException, ParserConfigurationException {
		this.file = file;
		this.fullPath = fullPath;

		wsdlFile = new WsdlFile();
		wsdlFile.setFile(file);
		wsdlFile.setPathVersionPair(new VersionedFile(fullPath, 1, false));
		wsdlFile.setOperations(extract("operation", "name"));
	}

	public WsdlFile getWsdlFile() {
		return wsdlFile;
	}

	private List<String> extract(String extractedName, String extractedAttribute) throws ParserConfigurationException, IOException, SAXException {
		List<String> output = new ArrayList<>();
		List<Response> responses = new ArrayList<>();
		List<Request> requests = new ArrayList<>();



		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilderFactory.setNamespaceAware(true);
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc;

		doc = docBuilder.parse(new ByteArrayInputStream(file));

		NodeList list = doc.getDocumentElement().getElementsByTagNameNS("*", extractedName);



		for (int i = 0; i < list.getLength(); i++) {
			Element type = (Element) list.item(i);
			if (type.hasAttribute("name")) {
				String item = type.getAttribute("name");
				if(item.contains(":")) {
					item = item.split(":")[1];
				}
				output.add(item);


				NodeList elementList = type.getElementsByTagNameNS("*", "input");
				for (int j = 0; j < elementList.getLength(); j++) {
					Element element = (Element) elementList.item(j);
					String requestName = "";
					if (element.hasAttribute("message")) {
						requestName = element.getAttribute("message");
						if(requestName.contains(":")) {
							requestName = requestName.split(":")[1];
						}
						requests.add(new Request(new Name(requestName),new Parent(item)));
					}


				}
				NodeList attributeList = type.getElementsByTagNameNS("*", "output");
				for (int k = 0; k < attributeList.getLength(); k++) {
					Element element = (Element) attributeList.item(k);
					String responseName = "";
					if (element.hasAttribute("message")) {
						responseName = element.getAttribute("message");
						if(responseName.contains(":")) {
							responseName = responseName.split(":")[1];
						}
						responses.add(new Response(new Name(responseName),new Parent(item)));
					}
				}
			}

		}

		wsdlFile.setRequests(requests);
		wsdlFile.setResponses(responses);


		return new ArrayList(new HashSet(output));
	}

}
