/**
 * 
 */
package org.kuali.student.rules.devgui.client.model;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;

/**
 * @author zzraly
 */
public class RulesHierarchyInfo implements ModelObject {
    private static final long serialVersionUID = 123123142351351L;

    private String agendaType;
    private String agendaDeterminationKeysSet;
    private String businessRuleType;
    private String businessRuleId;
    private String anchor;
    private String businessRuleDisplayName;
    private String status;

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUniqueId() {
        return businessRuleId;
    }

    /**
     * @return the businessRuleDisplayName
     */
    public final String getBusinessRuleDisplayName() {
        return businessRuleDisplayName;
    }

    /**
     * @param businessRuleDisplayName
     *            the businessRuleDisplayName to set
     */
    public final void setBusinessRuleDisplayName(String displayName) {
        this.businessRuleDisplayName = displayName;
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
     * @return the agendaDeterminationKeysSet
     */
    public final String getAgendaDeterminationKeysSet() {
        return agendaDeterminationKeysSet;
    }

    /**
     * @param agendaDeterminationKeysSet
     *            the agendaDeterminationKeysSet to set
     */
    public final void setAgendaDeterminationKeysSet(String agendaDeterminationKeysSet) {
        this.agendaDeterminationKeysSet = agendaDeterminationKeysSet;
    }

    /**
     * @return the businessRuleType
     */
    public final String getBusinessRuleType() {
        return businessRuleType;
    }

    /**
     * @param businessRuleType
     *            the businessRuleType to set
     */
    public final void setBusinessRuleType(String businessRuleType) {
        this.businessRuleType = businessRuleType;
    }

    /**
     * @return the businessRuleId
     */
    public final String getBusinessRuleId() {
        return businessRuleId;
    }

    /**
     * @param businessRuleId
     *            the businessRuleId to set
     */
    public final void setBusinessRuleId(String businessRuleId) {
        this.businessRuleId = businessRuleId;
    }

    /**
     * @return the anchor
     */
    public final String getAnchor() {
        return anchor;
    }

    /**
     * @param anchor
     *            the anchor to set
     */
    public final void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    @Override
    public final String toString() {
        return "agendaType: '" + agendaType + "', agendaDeterminationKeysSet: '" + agendaDeterminationKeysSet + "', businessRuleType: '" + businessRuleType + "', businessRuleId: '" + businessRuleId + "', anchor: '" + anchor + "', businessRuleDisplayName: '" + businessRuleDisplayName;
    }
}
