
package org.kuali.student.rules.rulesmanagement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "deleteBusinessRule", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteBusinessRule", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement")
public class DeleteBusinessRule {

    @XmlElement(name = "businessRuleId", namespace = "")
    private String businessRuleId;

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

}
