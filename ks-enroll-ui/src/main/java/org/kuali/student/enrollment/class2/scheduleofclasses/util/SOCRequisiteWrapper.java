package org.kuali.student.enrollment.class2.scheduleofclasses.util;

import org.kuali.rice.krms.api.repository.rule.RuleDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class contains all the required requisites for display on the ui
 *
 * @author Kuali Student Team
 */
public class SOCRequisiteWrapper {

    private StringBuilder coRequisite;
    private Map<String, Map<String, String>> aoRequisiteMap;

    private List<RuleDefinition> coRules;
    private Map<String, List<RuleDefinition>> aoToRulesMap;
    private Map<String, String> nlMap;
    private List<String> ruleTypes;

    public SOCRequisiteWrapper() {
        coRequisite = new StringBuilder();
        aoRequisiteMap = new HashMap<String, Map<String, String>>();
        ruleTypes = new ArrayList<String>();
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

    public List<RuleDefinition> getCoRules() {
        return coRules;
    }

    public void setCoRules(List<RuleDefinition> coRules) {
        this.coRules = coRules;
    }

    public Map<String, List<RuleDefinition>> getAoToRulesMap() {
        return aoToRulesMap;
    }

    public void setAoToRulesMap(Map<String, List<RuleDefinition>> aoToRulesMap) {
        this.aoToRulesMap = aoToRulesMap;
    }

    public Map<String, String> getNlMap() {
        return nlMap;
    }

    public void setNlMap(Map<String, String> nlMap) {
        this.nlMap = nlMap;
    }

    public List<String> getRuleTypes() {
        return ruleTypes;
    }

    public void setRuleTypes(List<String> ruleTypes) {
        this.ruleTypes = ruleTypes;
    }

    /**
     * Build string for display of AO requisites
     *
     * @param aoId
     * @return string of requisites
     */
    public String getRequisiteForAO(String aoId) {
        StringBuilder requisites = new StringBuilder();
        Map<String, String> requisiteMap = this.getAoRequisiteMap().get(aoId);

        for (String req : requisiteMap.values()) {
            requisites.append(req);
        }

        return requisites.toString();
    }
}
