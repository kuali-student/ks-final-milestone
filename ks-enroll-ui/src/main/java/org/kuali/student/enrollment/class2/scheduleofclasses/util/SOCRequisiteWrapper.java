package org.kuali.student.enrollment.class2.scheduleofclasses.util;

import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * This class contains all the required requisites for display on the ui
 *
 * @author Kuali Student Team
 */
public class SOCRequisiteWrapper {

    private Map<String, Map<String, String>> aoRequisiteTypeMap;
    private Map<String, String> coRequisiteTypeMap;
    private Set<String> ruleTypes;

    private StringBuilder coRequisite;
    private Map<String, StringBuilder> aoRequisiteMap;

    public SOCRequisiteWrapper() {
        aoRequisiteTypeMap = new HashMap<String, Map<String, String>>();
        coRequisiteTypeMap = new HashMap<String, String>();
        ruleTypes = new TreeSet<String>();
        coRequisite = new StringBuilder();
        aoRequisiteMap = new HashMap<String, StringBuilder>();
    }

    public Map<String, Map<String, String>> getAoRequisiteTypeMap() {
        return aoRequisiteTypeMap;
    }

    public void setAoRequisiteTypeMap(Map<String, Map<String, String>> aoRequisiteTypeMap) {
        this.aoRequisiteTypeMap = aoRequisiteTypeMap;
    }

    public Map<String, String> getCoRequisiteTypeMap() {
        return coRequisiteTypeMap;
    }

    public void setCoRequisiteTypeMap(Map<String, String> coRequisiteTypeMap) {
        this.coRequisiteTypeMap = coRequisiteTypeMap;
    }

    public Set<String> getRuleTypes() {
        return ruleTypes;
    }

    public void setRuleTypes(Set<String> ruleTypes) {
        this.ruleTypes = ruleTypes;
    }

    public StringBuilder getCoRequisite() {
        return coRequisite;
    }

    public void setCoRequisite(StringBuilder coRequisite) {
        this.coRequisite = coRequisite;
    }

    public Map<String, StringBuilder> getAoRequisiteMap() {
        return aoRequisiteMap;
    }

    public void setAoRequisiteMap(Map<String, StringBuilder> aoRequisiteMap) {
        this.aoRequisiteMap = aoRequisiteMap;
    }

    public void loadRequisites(List<ActivityOfferingWrapper> activityOfferingWrapperList) {
        Map<String, String> overriddenRules = new TreeMap<String, String>();

        for(String ruleType : ruleTypes) {
            if(!aoRequisiteTypeMap.isEmpty()) {
                for(Map.Entry<String, Map<String, String>> aoEntry : aoRequisiteTypeMap.entrySet()) {
                    if(coRequisiteTypeMap.containsKey(ruleType) && aoEntry.getValue().containsKey(ruleType)) {
                        overriddenRules.put(ruleType, coRequisiteTypeMap.get(ruleType));
                        coRequisiteTypeMap.remove(ruleType);
                    }
                }
            }
        }

        loadAORequisites(overriddenRules);

        if(!overriddenRules.isEmpty()) {
            loadcoOverridenRules(overriddenRules, activityOfferingWrapperList);
        }

        if(!coRequisiteTypeMap.isEmpty()) {
            loadCORequisites();
        }
    }

    private void loadAORequisites(Map<String, String> overriddenRules) {
        String aoRequisite;
        for(String ruleType : ruleTypes) {
            if(!aoRequisiteTypeMap.isEmpty()) {
                for(Map.Entry<String, Map<String, String>> aoEntry : aoRequisiteTypeMap.entrySet()) {
                    aoRequisite = new String();
                    if(aoEntry.getValue().containsKey(ruleType)) {
                        aoRequisite = aoEntry.getValue().get(ruleType);
                    } else if(!overriddenRules.isEmpty()) {
                        if(overriddenRules.containsKey(ruleType)) {
                            aoRequisite = overriddenRules.get(ruleType);
                        }
                    }

                    if(!aoRequisite.isEmpty()) {
                        if(aoRequisiteMap.containsKey(aoEntry.getKey())) {
                            aoRequisiteMap.get(aoEntry.getKey()).append(aoRequisite);
                            continue;
                        }
                        aoRequisiteMap.put(aoEntry.getKey(), new StringBuilder(aoRequisite));
                    }
                }
            }
        }
    }

    private void loadcoOverridenRules(Map<String, String> overriddenRules, List<ActivityOfferingWrapper> activityOfferingWrapperList) {
        for(ActivityOfferingWrapper activityOfferingWrapper : activityOfferingWrapperList) {
            if(activityOfferingWrapper.getRequisite() == null) {
                StringBuilder req = new StringBuilder();
                for(Map.Entry<String, String> rule : overriddenRules.entrySet()) {
                    req.append(rule.getValue());
                }
                activityOfferingWrapper.setRequisite(req.toString());
            }
        }
    }

    private void loadCORequisites() {
        for(Map.Entry<String, String> coReq : coRequisiteTypeMap.entrySet()) {
            coRequisite.append(coReq.getValue());
        }
    }
}
