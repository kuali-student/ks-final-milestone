
package org.kuali.student.core.organization.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.core.validation.dto.ValidationResult;

/**
 * This class was generated by Apache CXF 2.1.3
 * Fri Jan 16 11:42:39 EST 2009
 * Generated source version: 2.1.3
 */

@XmlRootElement(name = "validateOrgResponse", namespace = "http://org.kuali.student/core/organization")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateOrgResponse", namespace = "http://org.kuali.student/core/organization")

public class ValidateOrgResponse {

    @XmlElement(name = "return")
    private java.util.List<ValidationResult> _return;

    public java.util.List<ValidationResult> getReturn() {
		if(_return==null){
			_return = new java.util.ArrayList<ValidationResult>();
		}
        return this._return;
    }

    public void setReturn(java.util.List<ValidationResult> new_return)  {
        this._return = new_return;
    }

}

