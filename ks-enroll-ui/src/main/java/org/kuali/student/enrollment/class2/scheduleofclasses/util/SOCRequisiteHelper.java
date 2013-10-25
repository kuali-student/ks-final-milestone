package org.kuali.student.enrollment.class2.scheduleofclasses.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Helper class for building the requisites for display on Schedule of Classes screens
 *
 * @author Kuali Student Team
 */
public class SOCRequisiteHelper {

    private SOCRequisiteWrapper reqWrapper;

    public SOCRequisiteHelper() {
        reqWrapper = new SOCRequisiteWrapper();
    }

    public SOCRequisiteWrapper getReqWrapper() {
        return reqWrapper;
    }

    public void setReqWrapper(SOCRequisiteWrapper reqWrapper) {
        this.reqWrapper = reqWrapper;
    }

    /**
     * Build and populate requisites
     *
     * @param activityOfferingWrapperList
     */
    public void loadRequisites(List<ActivityOfferingWrapper> activityOfferingWrapperList) {
        Map<String, String> overriddenRules = new TreeMap<String, String>();

        //Populate map of overridden CO requisites
        for (String ruleType : reqWrapper.getRuleTypes()) {
            if (!reqWrapper.getAoRequisiteTypeMap().isEmpty()) {
                for (Map.Entry<String, Map<String, String>> aoEntry : reqWrapper.getAoRequisiteTypeMap().entrySet()) {
                    if (reqWrapper.getCoRequisiteTypeMap().containsKey(ruleType) && aoEntry.getValue().containsKey(ruleType)) {
                        overriddenRules.put(ruleType, reqWrapper.getCoRequisiteTypeMap().get(ruleType));
                        reqWrapper.getCoRequisiteTypeMap().remove(ruleType);
                    }
                }
            }
        }

        loadAORequisites(overriddenRules);

        if (!overriddenRules.isEmpty()) {
            loadcoOverridenRules(overriddenRules, activityOfferingWrapperList);
        }

        if (!reqWrapper.getCoRequisiteTypeMap().isEmpty()) {
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
        for (String ruleType : reqWrapper.getRuleTypes()) {
            if (!reqWrapper.getAoRequisiteTypeMap().isEmpty()) {
                for (Map.Entry<String, Map<String, String>> aoEntry : reqWrapper.getAoRequisiteTypeMap().entrySet()) {
                    aoRequisite = new String();
                    if (aoEntry.getValue().containsKey(ruleType)) {
                        String aoValue = StringUtils.substringAfter(aoEntry.getValue().get(ruleType), ":");
                        if(aoValue.length() > 1) {
                            aoRequisite = aoEntry.getValue().get(ruleType);
                        } else {
                            reqWrapper.getSuppressNullMap().put(aoEntry.getKey(), ruleType);
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
                        Map<String, String> temp = new TreeMap<String, String>();
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
                    if (reqWrapper.getSuppressNullMap().containsKey(activityOfferingWrapper.getActivityCode())) {
                        Map<String, String> temp = new HashMap<String, String>(overriddenRules);
                        temp.remove(reqWrapper.getSuppressNullMap().get(activityOfferingWrapper.getActivityCode()));
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
        for(Map.Entry<String, String> coReq : reqWrapper.getCoRequisiteTypeMap().entrySet()) {
            reqWrapper.getCoRequisite().append(coReq.getValue());
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
        for(int i = 0; i < registrationGroupWrapperList.size(); i = i + 2) {
            RegistrationGroupWrapper registrationGroupWrapper = registrationGroupWrapperList.get(i);

            firstReq = new StringBuilder();
            secondReq = new StringBuilder();
            commonReq = new StringBuilder();

            //Retrieve RegistrationGroupWrapper with same name
            RegistrationGroupWrapper partnerRegGroup = getRegistrationGroupWrapper(registrationGroupWrapperList, registrationGroupWrapper.getRgInfo().getName(), registrationGroupWrapper.getAoActivityCodeText());

            //Determine each RegistrationGroupWrapper's requisite and common requisite
            if(reqWrapper.getAoRequisiteMap().containsKey(registrationGroupWrapper.getAoActivityCodeText()) ||
                    reqWrapper.getAoRequisiteMap().containsKey(partnerRegGroup.getAoActivityCodeText())) {

                for(String rule : reqWrapper.getRuleTypes()) {
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
