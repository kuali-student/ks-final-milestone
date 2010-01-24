package org.kuali.student.brms.devgui.client.model;

import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;
import org.kuali.student.brms.rulemanagement.dto.RuleElementInfo;

public class BusinessRule extends BusinessRuleInfo {
    private static final long serialVersionUID = 123123142351351L;

    // TODO: outputStateSet and displayOutputStructure

    public int getMaxOrdinalPosition() {
        int max = 0;
        for (RuleElementInfo elem : this.getBusinessRuleElementList()) {
            if (elem.getOrdinalPosition() > max) {
                max = elem.getOrdinalPosition();
            }
        }
        return max;
    }

    public RuleElementInfo getBusinessRuleElement(Integer ordinalPosition) {
        for (RuleElementInfo elem : this.getBusinessRuleElementList()) {
            if ((elem.getBusinessRuleElemnetTypeKey().equals("PROPOSITION")) && (elem.getOrdinalPosition().equals(ordinalPosition))) {
                return elem;
            }
        }
        return null;
    }

    public void addBusinessRuleElement(RuleElementInfo newElem) {
        this.getBusinessRuleElementList().add(newElem);
    }

    public void removeBusinessRuleElement(Integer ordinalPosition) {
        for (RuleElementInfo elem : this.getBusinessRuleElementList()) {
            if (elem.getOrdinalPosition().equals(ordinalPosition)) {
                this.getBusinessRuleElementList().remove(elem);
                return;
            }
        }
    }
}