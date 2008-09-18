package org.kuali.student.rules.devgui.client.model;

import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;

public class BusinessRuleInfo extends BusinessRuleInfoDTO {
    private static final long serialVersionUID = 123123142351351L;

    // TODO: outputStateSet and displayOutputStructure

    public int getMaxOrdinalPosition() {
        int max = 0;
        for (RuleElementDTO elem : this.getRuleElementList()) {
            if (elem.getOrdinalPosition() > max) {
                max = elem.getOrdinalPosition();
            }
        }
        return max;
    }

    public RuleElementDTO getBusinessRuleElement(Integer ordinalPosition) {
        for (RuleElementDTO elem : this.getRuleElementList()) {
            if ((elem.getOperation().equals("PROPOSITION")) && (elem.getOrdinalPosition().equals(ordinalPosition))) {
                return elem;
            }
        }
        return null;
    }

    public void addBusinessRuleElement(RuleElementDTO newElem) {
        this.getRuleElementList().add(newElem);
    }

    public void removeBusinessRuleElement(Integer ordinalPosition) {
        for (RuleElementDTO elem : this.getRuleElementList()) {
            if (elem.getOrdinalPosition().equals(ordinalPosition)) {
                this.getRuleElementList().remove(elem);
                return;
            }
        }
    }
}