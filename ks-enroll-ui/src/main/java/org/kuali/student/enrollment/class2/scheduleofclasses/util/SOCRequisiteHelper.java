package org.kuali.student.enrollment.class2.scheduleofclasses.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for building the requisites for display on Schedule of Classes screens
 *
 * @author Kuali Student Team
 */
public class SOCRequisiteHelper {

    private SOCRequisiteWrapper reqWrapper;

    private Map<String, Map<String, String>> aoRequisiteTypeMap;
    private Map<String, String> coRequisiteTypeMap;
    private List<String> ruleTypes;
    private Map<String, String> suppressNullMap;

    public SOCRequisiteHelper() {
        reqWrapper = new SOCRequisiteWrapper();
        aoRequisiteTypeMap = new HashMap<String, Map<String, String>>();
        coRequisiteTypeMap = new HashMap<String, String>();
        ruleTypes = new ArrayList<String>();
        suppressNullMap = new HashMap<String, String>();
    }

    public SOCRequisiteWrapper getReqWrapper() {
        return reqWrapper;
    }

    public void setReqWrapper(SOCRequisiteWrapper reqWrapper) {
        this.reqWrapper = reqWrapper;
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

    public List<String> getRuleTypes() {
        return ruleTypes;
    }

    public void setRuleTypes(List<String> ruleTypes) {
        this.ruleTypes = ruleTypes;
    }

    public Map<String, String> getSuppressNullMap() {
        return suppressNullMap;
    }

    public void setSuppressNullMap(Map<String, String> suppressNullMap) {
        this.suppressNullMap = suppressNullMap;
    }

    /**
     * Build and populate requisites
     *
     * @param activityOfferingWrapperList
     */
    public void loadRequisites(List<ActivityOfferingWrapper> activityOfferingWrapperList) {
        Map<String, String> overriddenRules = new HashMap<String, String>();

        //Populate map of overridden CO requisites
        for (String ruleType : ruleTypes) {
            if (!aoRequisiteTypeMap.isEmpty()) {
                for (Map.Entry<String, Map<String, String>> aoEntry : aoRequisiteTypeMap.entrySet()) {
                    if (coRequisiteTypeMap.containsKey(ruleType) && aoEntry.getValue().containsKey(ruleType)) {
                        overriddenRules.put(ruleType, coRequisiteTypeMap.get(ruleType));
                        coRequisiteTypeMap.remove(ruleType);
                    }
                }
            }
        }

        loadAORequisites(overriddenRules);

        if (!overriddenRules.isEmpty()) {
            loadcoOverridenRules(overriddenRules, activityOfferingWrapperList);
        }

        if (!coRequisiteTypeMap.isEmpty()) {
            loadCORequisites();
        }
    }

    /**
     * Build map of AO requisites and add overridden requisites
     *
     * @param overriddenRules
     */
    private void loadAORequisites(Map<String, String> overriddenRules) {
        String aoRequisite;
        for (String ruleType : ruleTypes) {
            if (!aoRequisiteTypeMap.isEmpty()) {
                for (Map.Entry<String, Map<String, String>> aoEntry : aoRequisiteTypeMap.entrySet()) {
                    aoRequisite = new String();
                    if (aoEntry.getValue().containsKey(ruleType)) {
                        String aoValue = StringUtils.substringAfter(aoEntry.getValue().get(ruleType), ":");
                        if(aoValue.length() > 1) {
                            aoRequisite = aoEntry.getValue().get(ruleType);
                        } else {
                            suppressNullMap.put(aoEntry.getKey(), ruleType);
                        }
                    } else if (!overriddenRules.isEmpty()) {
                        if (overriddenRules.containsKey(ruleType)) {
                            aoRequisite = overriddenRules.get(ruleType);
                        }
                    }

                    if (!aoRequisite.isEmpty()) {
                        if (reqWrapper.getAoRequisiteMap().containsKey(aoEntry.getKey())) {
                            reqWrapper.getAoRequisiteMap().get(aoEntry.getKey()).put(ruleType, aoRequisite);
                            continue;
                        }
                        Map<String, String> temp = new HashMap<String, String>();
                        temp.put(ruleType, aoRequisite);
                        reqWrapper.getAoRequisiteMap().put(aoEntry.getKey(), temp);
                    }
                }
            }
        }
    }

    /**
     * Populate AO requisite map with overridden requisites for all outstanding ActivityOfferingWrappers
     *
     * @param overriddenRules
     * @param activityOfferingWrapperList
     */
    private void loadcoOverridenRules(Map<String, String> overriddenRules, List<ActivityOfferingWrapper> activityOfferingWrapperList) {
        for (ActivityOfferingWrapper activityOfferingWrapper : activityOfferingWrapperList) {
            if(activityOfferingWrapper.getRequisite() == null) {
                if(!reqWrapper.getAoRequisiteMap().containsKey(activityOfferingWrapper.getActivityCode())) {
                    if (suppressNullMap.containsKey(activityOfferingWrapper.getActivityCode())) {
                        Map<String, String> temp = new HashMap<String, String>(overriddenRules);
                        temp.remove(suppressNullMap.get(activityOfferingWrapper.getActivityCode()));
                        reqWrapper.getAoRequisiteMap().put(activityOfferingWrapper.getActivityCode(), temp);
                        continue;
                    }
                    reqWrapper.getAoRequisiteMap().put(activityOfferingWrapper.getActivityCode(), overriddenRules);
                }
            }
        }
    }

    /**
     * Populate CO requisites with requisites not overridden
     */
    private void loadCORequisites() {
        for (String ruleType : ruleTypes) {
            for(Map.Entry<String, String> coReq : coRequisiteTypeMap.entrySet()) {
                if (coReq.getKey().contains(ruleType)) {
                    reqWrapper.getCoRequisite().append(coReq.getValue());
                }
            }
        }
    }

    /**
     * Build and populate RegistrationGroupWrappers requisites from AO requisite map
     *
     * @param registrationGroupWrapperList
     */
    public void loadRegRequisites(List<RegistrationGroupWrapper> registrationGroupWrapperList) {
        StringBuilder firstReq;
        StringBuilder secondReq;
        StringBuilder commonReq;

        //For each RegistrationGroupWrapper and its partner with same name
        for(int i = 0; i < registrationGroupWrapperList.size(); i++) {
            RegistrationGroupWrapper registrationGroupWrapper = registrationGroupWrapperList.get(i);

            firstReq = new StringBuilder();
            secondReq = new StringBuilder();
            commonReq = new StringBuilder();

            //Retrieve RegistrationGroupWrapper with same name
            RegistrationGroupWrapper partnerRegGroup = getRegistrationGroupWrapper(registrationGroupWrapperList, registrationGroupWrapper.getRgInfo().getName(), registrationGroupWrapper.getAoActivityCodeText());
            if (!partnerRegGroup.getAoActivityCodeText().equals("null")) {
                i++;
            }
            //Determine each RegistrationGroupWrapper's requisite and common requisite
            if(reqWrapper.getAoRequisiteMap().containsKey(registrationGroupWrapper.getAoActivityCodeText()) ||
                    reqWrapper.getAoRequisiteMap().containsKey(partnerRegGroup.getAoActivityCodeText())) {

                for(String rule : ruleTypes) {
                    Map<String, String> firstReqMap = reqWrapper.getAoRequisiteMap().get(registrationGroupWrapper.getAoActivityCodeText());
                    Map<String, String> secondReqMap = reqWrapper.getAoRequisiteMap().get(partnerRegGroup.getAoActivityCodeText());

                    if (firstReqMap != null && secondReqMap != null) {
                        if (firstReqMap.containsKey(rule) && secondReqMap.containsKey(rule)) {
                            if (firstReqMap.get(rule).equals(secondReqMap.get(rule))) {
                                commonReq.append(firstReqMap.get(rule));
                                continue;
                            }
                        }
                    }
                    if (firstReqMap != null) {
                        if (firstReqMap.containsKey(rule)) {
                            firstReq.append(firstReqMap.get(rule));
                        }
                    }
                    if (secondReqMap != null) {
                        if (secondReqMap.containsKey(rule)) {
                            secondReq.append(secondReqMap.get(rule));
                        }
                    }
                }
                //Set requisite on RegistrationGroupWrapper
                if (!commonReq.toString().isEmpty()) {
                    registrationGroupWrapper.setCommonRequisite(commonReq.toString());
                }
                if (!firstReq.toString().isEmpty()) {
                    registrationGroupWrapper.setRequisite(firstReq.toString());
                }
                if (!secondReq.toString().isEmpty()) {
                    partnerRegGroup.setRequisite(secondReq.toString());
                }
            }
        }
    }

    private RegistrationGroupWrapper getRegistrationGroupWrapper(List<RegistrationGroupWrapper> registrationGroupWrapperList, String name, String aoCode) {
        for (RegistrationGroupWrapper registrationGroupWrapper : registrationGroupWrapperList) {
            if(registrationGroupWrapper.getRgInfo().getName().equals(name) && !registrationGroupWrapper.getAoActivityCodeText().equals(aoCode)) {
                return registrationGroupWrapper;
            }
        }
        RegistrationGroupWrapper emptyRegistrationGroupWrapper = new RegistrationGroupWrapper();
        emptyRegistrationGroupWrapper.setAoActivityCodeText("null");
        return emptyRegistrationGroupWrapper;
    }

    /**
     * Build string for display of AO requisites
     *
     * @param aoCode
     * @return string of requisites
     */
    public String prepareAORequisites(String aoCode) {
        StringBuilder requisites = new StringBuilder();
        Map<String, String> requisiteMap = reqWrapper.getAoRequisiteMap().get(aoCode);

        for (String req : requisiteMap.values()) {
            requisites.append(req);
        }

        return requisites.toString();
    }
}
