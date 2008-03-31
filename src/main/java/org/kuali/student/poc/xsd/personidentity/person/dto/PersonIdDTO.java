package org.kuali.student.poc.xsd.personidentity.person.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonIdDTO {

    @XmlAttribute
    long id;

    public PersonIdDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public PersonIdDTO(long personId) {
        super();
        this.id = personId;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the id
     */
    public void setId(long id) {
        this.id = id;
    }
    
}
