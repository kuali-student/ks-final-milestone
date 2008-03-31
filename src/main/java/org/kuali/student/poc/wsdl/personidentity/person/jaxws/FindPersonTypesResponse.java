
package org.kuali.student.poc.wsdl.personidentity.person.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeDTO;

/**
 * 
 * hand edited to include the generics.
 * 
 */

@XmlRootElement(name = "findPersonTypesResponse", namespace = "http://student.kuali.org/poc/wsdl/personidentity/person")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findPersonTypesResponse", namespace = "http://student.kuali.org/poc/wsdl/personidentity/person")

public class FindPersonTypesResponse {

    @XmlElement(name = "return")
    private java.util.List<PersonTypeDTO> _return;

    public java.util.List<PersonTypeDTO> get_return() {
        return this._return;
    }
    
    public void set_return( java.util.List<PersonTypeDTO> new_return ) {
        this._return = new_return;
    }
    
}

