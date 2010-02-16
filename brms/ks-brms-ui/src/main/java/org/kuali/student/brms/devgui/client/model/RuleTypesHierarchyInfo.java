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
