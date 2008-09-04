
package org.kuali.student.rules.rulesmanagement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.kuali.student.rules.rulesmanagement.dto.BusinessRuleInfoDTO;

@XmlRootElement(name = "createBusinessRule", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createBusinessRule", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement")
public class CreateBusinessRule {

    @XmlElement(name = "businessRuleInfo", namespace = "")
    private BusinessRuleInfoDTO businessRuleInfo;

    /**
     * 
     * @return
     *     returns BusinessRuleInfoDTO
     */
    public BusinessRuleInfoDTO getBusinessRuleInfo() {
        return this.businessRuleInfo;
    }

    /**
     * 
     * @param businessRuleInfo
     *     the value for the businessRuleInfo property
     */
    public void setBusinessRuleInfo(BusinessRuleInfoDTO businessRuleInfo) {
        this.businessRuleInfo = businessRuleInfo;
    }

}
