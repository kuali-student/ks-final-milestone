
package org.kuali.student.rules.rulesmanagement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.kuali.student.rules.rulesmanagement.dto.StatusDTO;

@XmlRootElement(name = "deleteBusinessRuleResponse", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteBusinessRuleResponse", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement")
public class DeleteBusinessRuleResponse {

    @XmlElement(name = "return", namespace = "")
    private StatusDTO _return;

    /**
     * 
     * @return
     *     returns StatusDTO
     */
    public StatusDTO getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(StatusDTO _return) {
        this._return = _return;
    }

}
