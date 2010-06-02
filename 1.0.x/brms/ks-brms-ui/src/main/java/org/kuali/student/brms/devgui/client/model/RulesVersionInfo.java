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

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;

public class RulesVersionInfo implements ModelObject {
    
    private String agendaType;
    private String agendaDeterminationKeysSet;
    private String businessRuleType;
    private String businessRuleId;
    private String businessRuleOriginalId;
    private String anchor; // the group key of this group
    private String businessRuleDisplayName;
    private String status;
    private java.util.Date effectiveDate;
    private java.util.Date expirationDate;
    
    public String getUniqueId() {
        return getBusinessRuleId();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
    
    
    public String getBusinessRuleOriginalId() {
        return businessRuleOriginalId;
    }

    public void setBusinessRuleOriginalId(String businessRuleOriginalId) {
        this.businessRuleOriginalId = businessRuleOriginalId;
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
    
    public java.util.Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(java.util.Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public java.util.Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDateDate(java.util.Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setValuesFromDTO(BusinessRuleInfo businessRuleInfoDTO) {
        setBusinessRuleType(businessRuleInfoDTO.getType());
        setBusinessRuleId(businessRuleInfoDTO.getId());
        setStatus(businessRuleInfoDTO.getState());
        setBusinessRuleId(businessRuleInfoDTO.getId());
        setBusinessRuleOriginalId(businessRuleInfoDTO.getOriginalRuleId());
        setAnchor(businessRuleInfoDTO.getAnchor());
        setBusinessRuleDisplayName(businessRuleInfoDTO.getName());
        setStatus(businessRuleInfoDTO.getState());
        setEffectiveDate(businessRuleInfoDTO.getEffectiveDate());
        setExpirationDateDate(businessRuleInfoDTO.getExpirationDate());
    }

    @Override
    public final String toString() {
        String result =
            "agendaType: '" + agendaType + 
            "', agendaDeterminationKeysSet: '" + agendaDeterminationKeysSet + 
            "', businessRuleType: '" + businessRuleType + 
            "', businessRuleId: '" + businessRuleId + 
            "', anchor: '" + anchor + 
            "', businessRuleDisplayName: '" + businessRuleDisplayName +
            "', effectiveDate: '" + effectiveDate +
            ";, expirationDate: '" + expirationDate;
        return result;
    }

}
