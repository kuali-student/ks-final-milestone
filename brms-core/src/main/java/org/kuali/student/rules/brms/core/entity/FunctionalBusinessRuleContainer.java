package org.kuali.student.rules.brms.core.entity;

import java.util.ArrayList;
import java.util.List;

public class FunctionalBusinessRuleContainer {
    private String namespace;
    private String description;
    private List<FunctionalBusinessRule> functionalBusinessRuleList = new ArrayList<FunctionalBusinessRule>();;
    
    public FunctionalBusinessRuleContainer(String namespace, String description) {
        super();
        this.namespace = namespace;
        this.description = description;
    }

    public List<FunctionalBusinessRule> getFunctionalBusinessRules() {
        return functionalBusinessRuleList;
    }

    public void addFunctionalBusinessRule(FunctionalBusinessRule functionalBusinessRule) {
        this.functionalBusinessRuleList.add(functionalBusinessRule);
    }

    public String getNamespace() {
        return namespace;
    }

    public String getDescription() {
        return description;
    }
}
