
package org.kuali.student.core.statement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Wed May 12 12:54:47 PDT 2010
 * Generated source version: 2.2
 */

@Deprecated
@XmlRootElement(name = "updateReqComponent", namespace = "http://student.kuali.org/wsdl/statement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateReqComponent", namespace = "http://student.kuali.org/wsdl/statement", propOrder = {"reqComponentId","reqComponentInfo"})

public class UpdateReqComponent {

    @XmlElement(name = "reqComponentId")
    private java.lang.String reqComponentId;
    @XmlElement(name = "reqComponentInfo")
    private org.kuali.student.core.statement.dto.ReqComponentInfo reqComponentInfo;

    public java.lang.String getReqComponentId() {
        return this.reqComponentId;
    }

    public void setReqComponentId(java.lang.String newReqComponentId)  {
        this.reqComponentId = newReqComponentId;
    }

    public org.kuali.student.core.statement.dto.ReqComponentInfo getReqComponentInfo() {
        return this.reqComponentInfo;
    }

    public void setReqComponentInfo(org.kuali.student.core.statement.dto.ReqComponentInfo newReqComponentInfo)  {
        this.reqComponentInfo = newReqComponentInfo;
    }

}

