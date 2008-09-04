
package org.kuali.student.rules.rulesmanagement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "findBusinessRuleIdsByBusinessRuleType", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findBusinessRuleIdsByBusinessRuleType", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement")
public class FindBusinessRuleIdsByBusinessRuleType {

    @XmlElement(name = "businessRuleTypeKey", namespace = "")
    private String businessRuleTypeKey;

    /**
     * 
     * @return
     *     returns String
     */
    public String getBusinessRuleTypeKey() {
        return this.businessRuleTypeKey;
    }

    /**
     * 
     * @param businessRuleTypeKey
     *     the value for the businessRuleTypeKey property
     */
    public void setBusinessRuleTypeKey(String businessRuleTypeKey) {
        this.businessRuleTypeKey = businessRuleTypeKey;
    }

}
