package org.kuali.student.rules.factfinder.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class FactStructureDisplayDTO implements Serializable {

    @XmlElement
    private String factStructureId;
    
    @XmlElement
    private String name;
    
    @XmlElement
    private String description;

    /**
     * @return the factStructureId
     */
    public String getFactStructureId() {
        return factStructureId;
    }

    /**
     * @param factStructureId the factStructureId to set
     */
    public void setFactStructureId(String factStructureId) {
        this.factStructureId = factStructureId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
