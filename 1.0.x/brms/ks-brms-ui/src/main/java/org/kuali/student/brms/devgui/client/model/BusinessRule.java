/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

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