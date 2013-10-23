package org.kuali.student.enrollment.class2.scheduleofclasses.util;

import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
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

    public void loadRequisites(List<ActivityOfferingWrapper> activityOfferingWrapperList) {
        Map<String, String> overriddenRules = new TreeMap<String, String>();

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

    private void loadAORequisites(Map<String, String> overriddenRules) {
        String aoRequisite;
        for (String ruleType : reqWrapper.getRuleTypes()) {
            if (!reqWrapper.getAoRequisiteTypeMap().isEmpty()) {
                for (Map.Entry<String, Map<String, String>> aoEntry : reqWrapper.getAoRequisiteTypeMap().entrySet()) {
                    aoRequisite = new String();
                    if (aoEntry.getValue().containsKey(ruleType)) {
                        aoRequisite = aoEntry.getValue().get(ruleType);
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

    private void loadcoOverridenRules(Map<String, String> overriddenRules, List<ActivityOfferingWrapper> activityOfferingWrapperList) {
        for (ActivityOfferingWrapper activityOfferingWrapper : activityOfferingWrapperList) {
            if(activityOfferingWrapper.getRequisite() == null) {
                if(!reqWrapper.getAoRequisiteMap().containsKey(activityOfferingWrapper.getActivityCode())) {
                    reqWrapper.getAoRequisiteMap().put(activityOfferingWrapper.getActivityCode(), overriddenRules);
                }
            }
        }
    }

    private void loadCORequisites() {
        for(Map.Entry<String, String> coReq : reqWrapper.getCoRequisiteTypeMap().entrySet()) {
            reqWrapper.getCoRequisite().append(coReq.getValue());
        }
    }

    public void loadRegRequisites(List<RegistrationGroupWrapper> registrationGroupWrapperList) {
        StringBuilder firstReq;
        StringBuilder secondReq;
        StringBuilder commonReq;

        for(int i = 0; i < registrationGroupWrapperList.size(); i = i + 2) {
            RegistrationGroupWrapper registrationGroupWrapper = registrationGroupWrapperList.get(i);

            firstReq = new StringBuilder();
            secondReq = new StringBuilder();
            commonReq = new StringBuilder();

            if(reqWrapper.getAoRequisiteMap().containsKey(registrationGroupWrapper.getAoActivityCodeText())) {
                RegistrationGroupWrapper partnerRegGroup = getPartnerRegGroup(registrationGroupWrapperList, registrationGroupWrapper.getRgInfo().getName(), registrationGroupWrapper.getAoActivityCodeText());
                for(String rule : reqWrapper.getRuleTypes()) {
                    Map<String, String> firstReqMap = reqWrapper.getAoRequisiteMap().get(registrationGroupWrapper.getAoActivityCodeText());
                    Map<String, String> secondReqMap = reqWrapper.getAoRequisiteMap().get(partnerRegGroup.getAoActivityCodeText());
                    if (firstReqMap.containsKey(rule) && secondReqMap.containsKey(rule)) {
                        if (firstReqMap.get(rule).equals(secondReqMap.get(rule))) {
                            commonReq.append(firstReqMap.get(rule));
                            continue;
                        }
                    }
                    if (firstReqMap.containsKey(rule)) {
                        firstReq.append(firstReqMap.get(rule));
                    }
                    if (secondReqMap.containsKey(rule)) {
                        secondReq.append(secondReqMap.get(rule));
                    }
                }
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

    private RegistrationGroupWrapper getPartnerRegGroup(List<RegistrationGroupWrapper> registrationGroupWrapperList, String name, String aoCode) {
        for (RegistrationGroupWrapper registrationGroupWrapper : registrationGroupWrapperList) {
            if(registrationGroupWrapper.getRgInfo().getName().equals(name) && !registrationGroupWrapper.getAoActivityCodeText().equals(aoCode)) {
                return registrationGroupWrapper;
            }
        }

        return null;
    }

    public String prepareAORequisites(String aoCode) {
        StringBuilder requisites = new StringBuilder();
        Map<String, String> requisiteMap = reqWrapper.getAoRequisiteMap().get(aoCode);

        for (String req : requisiteMap.values()) {
            requisites.append(req);
        }

        return requisites.toString();
    }
}
