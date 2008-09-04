
package org.kuali.student.rules.rulesmanagement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.kuali.student.rules.rulesmanagement.dto.BusinessRuleInfoDTO;

@XmlRootElement(name = "updateBusinessRule", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateBusinessRule", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement", propOrder = {
    "businessRuleId",
    "businessRuleInfo"
})
public class UpdateBusinessRule {

    @XmlElement(name = "businessRuleId", namespace = "")
    private String businessRuleId;
    @XmlElement(name = "businessRuleInfo", namespace = "")
    private BusinessRuleInfoDTO businessRuleInfo;

    /**
     * 
     * @return
     *     returns String
     */
    public String getBusinessRuleId() {
        return this.businessRuleId;
    }

    /**
     * 
     * @param businessRuleId
     *     the value for the businessRuleId property
     */
    public void setBusinessRuleId(String businessRuleId) {
        this.businessRuleId = businessRuleId;
    }

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
