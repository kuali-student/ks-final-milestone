package org.kuali.student.r2.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.OperationStatus;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OperationStatusInfo", propOrder = {"status", "messages", "warnings", "errors", "_futureElements"})

public class OperationStatusInfo implements OperationStatus, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String status;
    @XmlElement
    private List<String> messages;
    @XmlElement
    private List<String> warnings;
    @XmlElement
    private List<String> errors;

    @XmlAnyElement
    private List<Element> _futureElements;

    public OperationStatusInfo() {
        super();
        this.status = null;
        this.messages = new ArrayList<String>();
        this.warnings = new ArrayList<String>();
        this.errors = new ArrayList<String>();
        this._futureElements = null;
    }
    
    public OperationStatusInfo (OperationStatus orig) {
        this ();
        if (orig != null) {
            this.status = orig.getStatus();
            this.messages = new ArrayList (orig.getMessages());
        }
    }

    @Override
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
    
    @Override
    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }
    @Override
    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

}
