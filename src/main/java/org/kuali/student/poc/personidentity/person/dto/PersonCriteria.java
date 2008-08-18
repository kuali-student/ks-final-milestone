package org.kuali.student.poc.personidentity.person.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

//@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonCriteria implements Serializable{

	private static final long serialVersionUID = 1L;

	@XmlElement
	private String firstName = "%";
    @XmlElement
	private String lastName = "%";
    
    public PersonCriteria() {
        
    }
    public PersonCriteria(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
    }
    
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
