package org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class LuiPersonRelationCriteria implements Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
