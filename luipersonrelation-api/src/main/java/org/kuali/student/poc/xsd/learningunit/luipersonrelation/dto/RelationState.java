package org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class RelationState implements Serializable {

    private static final long serialVersionUID = 8217516385088217534L;
    
    @XmlElement
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
