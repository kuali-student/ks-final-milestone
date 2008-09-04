
package org.kuali.student.rules.rulesmanagement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.kuali.student.rules.rulesmanagement.dto.BusinessRuleInfoDTO;

@XmlRootElement(name = "fetchDetailedBusinessRuleInfoResponse", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchDetailedBusinessRuleInfoResponse", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement")
public class FetchDetailedBusinessRuleInfoResponse {

    @XmlElement(name = "return", namespace = "")
    private BusinessRuleInfoDTO _return;

    /**
     * 
     * @return
     *     returns BusinessRuleInfoDTO
     */
    public BusinessRuleInfoDTO getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(BusinessRuleInfoDTO _return) {
        this._return = _return;
    }

}
