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

/**
 * 
 */
package org.kuali.student.brms.devgui.client.model;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;

/**
 * @author zzraly
 */
public class RuleTypesHierarchyInfo implements ModelObject {
    private static final long serialVersionUID = 123123142351353L;

    private String agendaType;
    private String businessRuleTypeKey;
    private String anchorTypeKey;

    public String getAnchorTypeKey() {
		return anchorTypeKey;
	}

	public void setAnchorTypeKey(String anchorTypeKey) {
		this.anchorTypeKey = anchorTypeKey;
	}

	public String getUniqueId() {
        return businessRuleTypeKey;
    }

    /**
     * @return the agendaType
     */
    public final String getAgendaType() {
        return agendaType;
    }

    /**
     * @param agendaType
     *            the agendaType to set
     */
    public final void setAgendaType(String agendaType) {
        this.agendaType = agendaType;
    }

    /**
     * @return the businessRuleTypeKey
     */
    public final String getBusinessRuleTypeKey() {
        return businessRuleTypeKey;
    }

    /**
     * @param businessRuleTypeKey
     *            the businessRuleTypeKey to set
     */
    public final void setBusinessRuleTypeKey(String businessRuleTypeKey) {
        this.businessRuleTypeKey = businessRuleTypeKey;
    }

}
