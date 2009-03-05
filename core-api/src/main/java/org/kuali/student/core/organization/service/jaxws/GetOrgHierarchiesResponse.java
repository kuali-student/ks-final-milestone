
package org.kuali.student.core.organization.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.core.organization.dto.OrgHierarchyInfo;

/**
 * This class was generated by Apache CXF 2.1.3
 * Fri Jan 16 11:42:38 EST 2009
 * Generated source version: 2.1.3
 */

@XmlRootElement(name = "getOrgHierarchiesResponse", namespace = "http://org.kuali.student/core/organization")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getOrgHierarchiesResponse", namespace = "http://org.kuali.student/core/organization")

public class GetOrgHierarchiesResponse {

    @XmlElement(name = "return")
    private java.util.List<OrgHierarchyInfo> _return;

    public java.util.List<OrgHierarchyInfo> getReturn() {
		if(_return==null){
			_return = new java.util.ArrayList<OrgHierarchyInfo>();
		}
        return this._return;
    }

    public void setReturn(java.util.List<OrgHierarchyInfo> new_return)  {
        this._return = new_return;
    }

}

