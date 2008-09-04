
package org.kuali.student.rules.rulesmanagement.service.jaxws;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.kuali.student.rules.rulesmanagement.dto.BusinessRuleAnchorDTO;

@XmlRootElement(name = "fetchBusinessRuleInfoList", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchBusinessRuleInfoList", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement")
public class FetchBusinessRuleInfoList {

    @XmlElement(name = "businessRuleAnchorInfoList", namespace = "")
    private List<BusinessRuleAnchorDTO> businessRuleAnchorInfoList;

    /**
     * 
     * @return
     *     returns List<BusinessRuleAnchorDTO>
     */
    public List<BusinessRuleAnchorDTO> getBusinessRuleAnchorInfoList() {
        return this.businessRuleAnchorInfoList;
    }

    /**
     * 
     * @param businessRuleAnchorInfoList
     *     the value for the businessRuleAnchorInfoList property
     */
    public void setBusinessRuleAnchorInfoList(List<BusinessRuleAnchorDTO> businessRuleAnchorInfoList) {
        this.businessRuleAnchorInfoList = businessRuleAnchorInfoList;
    }

}
