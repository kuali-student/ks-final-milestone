package org.kuali.student.enrollment.class2.scheduleofclasses.util;

import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;

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
    private Map<String, Map<String, String>> aoRequisiteMap;

    public SOCRequisiteWrapper() {
        aoRequisiteTypeMap = new HashMap<String, Map<String, String>>();
        coRequisiteTypeMap = new HashMap<String, String>();
        ruleTypes = new TreeSet<String>();
        coRequisite = new StringBuilder();
        aoRequisiteMap = new HashMap<String, Map<String, String>>();
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

    public Map<String, Map<String,String>> getAoRequisiteMap() {
        return aoRequisiteMap;
    }

    public void setAoRequisiteMap(Map<String, Map<String,String>> aoRequisiteMap) {
        this.aoRequisiteMap = aoRequisiteMap;
    }
}
