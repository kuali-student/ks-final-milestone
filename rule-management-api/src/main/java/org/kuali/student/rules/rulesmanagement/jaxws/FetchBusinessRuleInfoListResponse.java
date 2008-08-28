
package org.kuali.student.rules.rulesmanagement.service.jaxws;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.kuali.student.rules.rulesmanagement.dto.BusinessRuleInfoDTO;

@XmlRootElement(name = "fetchBusinessRuleInfoListResponse", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchBusinessRuleInfoListResponse", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement")
public class FetchBusinessRuleInfoListResponse {

    @XmlElement(name = "return", namespace = "")
    private List<BusinessRuleInfoDTO> _return;

    /**
     * 
     * @return
     *     returns List<BusinessRuleInfoDTO>
     */
    public List<BusinessRuleInfoDTO> getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(List<BusinessRuleInfoDTO> _return) {
        this._return = _return;
    }

}
