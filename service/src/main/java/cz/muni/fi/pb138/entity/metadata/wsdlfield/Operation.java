package cz.muni.fi.pb138.entity.metadata.wsdlfield;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author Ondřej Gasior
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Operation {

    @XmlValue
    private String operation;

    public Operation(String operation) {
        this.operation = operation;
    }

    public Operation() {

    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
