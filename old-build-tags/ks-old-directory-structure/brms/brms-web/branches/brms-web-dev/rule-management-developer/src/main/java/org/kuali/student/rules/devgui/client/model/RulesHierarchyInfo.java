/**
 * 
 */
package org.kuali.student.rules.devgui.client.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;

/**
 * @author zzraly
 */
public class RulesHierarchyInfo implements ModelObject {
    private static final long serialVersionUID = 123123142351351L;


    private List<RulesVersionInfo> group;
    // The item that will be shown on GUI
    private RulesVersionInfo keyItem;
    
    public RulesHierarchyInfo() {
        group = new ArrayList<RulesVersionInfo>();
    }

    public String getUniqueId() {
        return getGroupBusinessRuleOriginalId();
    }
    
    /**
     * Gets the key used in this group of rule versions
     * @return
     */
    public String getGroupAnchor() {
        return (keyItem == null || keyItem.getAnchor() == null)? null :
            keyItem.getAnchor();
    }

    public void add(RulesVersionInfo rulesVersionInfo) {
        String originalId = getGroupBusinessRuleOriginalId();
        if (originalId != null &&
                rulesVersionInfo.getBusinessRuleOriginalId() != null &&
                !rulesVersionInfo.getBusinessRuleOriginalId().equals(originalId)) {
            throw new 
            IllegalArgumentException(
                    "Cannot add item with a different originalId in " +
                    "this group with originalId " + originalId);
        }
        if (keyItem == null) {
            keyItem = rulesVersionInfo;
        } else {
            if (rulesVersionInfo.getEffectiveDate()
                    .compareTo(keyItem.getEffectiveDate())
                    < 0) {
                keyItem = rulesVersionInfo;
            }
        }
        group.add(rulesVersionInfo);
    }
        
    public String getGroupDisplayName() {
        String result = "";
        if (keyItem == null) return null;
        result = keyItem.getBusinessRuleDisplayName();
        return result;
    }
    
    public String getGroupStatus() {
        String result = "";
        if (keyItem == null) return null;
        result = keyItem.getStatus();
        return result;
    }

    /**
     * @return the businessRuleDisplayName
     */
    public final String getGroupBusinessRuleDisplayName() {
        String result = "";
        if (keyItem == null) return null;
        result = keyItem.getBusinessRuleDisplayName();
        return result;
    }

    /**
     * @return the agendaType
     */
    public final String getGroupAgendaType() {
        String result = "";
        if (keyItem == null) return null;
        result = keyItem.getAgendaType();
        return result;
    }

    /**
     * @return the agendaDeterminationKeysSet
     */
    public final String getGroupAgendaDeterminationKeysSet() {
        String result = "";
        if (keyItem == null) return null;
        result = keyItem.getAgendaDeterminationKeysSet();
        return result;
    }

    /**
     * @return the businessRuleType
     */
    public final String getGroupBusinessRuleType() {
        String result = "";
        if (keyItem == null) return null;
        result = keyItem.getBusinessRuleType();
        return result;
    }

    /**
     * @return the businessRuleId
     */
    public final String getGroupBusinessRuleId() {
        String result = "";
        if (keyItem == null) return null;
        result = keyItem.getBusinessRuleId();
        return result;
    }

    public String getGroupBusinessRuleOriginalId() {
        String result = "";
        if (keyItem == null) return null;
        result = keyItem.getBusinessRuleOriginalId();
        return result;
    }

    public List<RulesVersionInfo> getVersions() {
        return group;
    }
    
    public List<RulesVersionInfo> getActiveVersions() {
        List<RulesVersionInfo> activeVersions = new ArrayList<RulesVersionInfo>();
        if (group != null) {
            for (RulesVersionInfo version : group) {
                String status = (version == null || version.getStatus() == null)? "" :
                    version.getStatus();
                status = (status == null)? "" : status;
                if (status.equals(BusinessRuleStatus.ACTIVE.name())) {
                    activeVersions.add(version);
                }
            }
        }
        return activeVersions;
    }
    
    public boolean isActiveVersionExist() {
        boolean result = false;
        List<RulesVersionInfo> activeVersions = getActiveVersions();
        if (activeVersions != null && !activeVersions.isEmpty()) {
            result = true;
        }
        return result;
    }
    
    @Override
    public final String toString() {
        String result =
            "agendaType: '" + getGroupAgendaType() + 
            "', agendaDeterminationKeysSet: '" + getGroupAgendaDeterminationKeysSet() + 
            "', businessRuleType: '" + getGroupBusinessRuleType() + 
            "', businessRuleId: '" + getGroupBusinessRuleId() + 
            "', anchor: '" + getGroupAnchor() + 
            "', businessRuleDisplayName: '" + getGroupBusinessRuleDisplayName();
        return result;
    }

}
