package cz.muni.fi.pb138.entity.metadata.wsdlfield;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by gasior on 22.06.2016.
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
