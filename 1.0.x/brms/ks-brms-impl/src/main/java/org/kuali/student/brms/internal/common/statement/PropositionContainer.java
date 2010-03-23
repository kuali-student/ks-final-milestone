/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.brms.internal.common.statement;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.kuali.student.brms.internal.common.statement.propositions.rules.RuleProposition;
import org.kuali.student.brms.internal.common.statement.report.RuleReport;

/**
 * PropositionContainer is a representation of the functional business rule and is composed of the
 * individual propositions in the rule and the boolean operators that join them
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 *
 */
public class PropositionContainer  implements java.io.Serializable {
	/** Class serial version uid */
    private static final long serialVersionUID = 1L;

    protected String functionalRuleString;
    protected Map<String,RuleProposition> propositionMap = new HashMap<String, RuleProposition>();
    protected Boolean ruleResult = false;
    protected RuleReport ruleReport = new RuleReport();
    protected Boolean overrideReport = false;
    
    /**
     * @return the functionalRuleString
     */
    public String getFunctionalRuleString() {
        return functionalRuleString;
    }
    /**
     * @param functionalRuleString the functionalRuleString to set
     */
    public void setFunctionalRuleString(String functionalRuleString) {
        this.functionalRuleString = functionalRuleString;
    }
    /**
     * @return the propositionMap
     */
    public Map<String, RuleProposition> getPropositionMap() {
        return propositionMap;
    }
    
    /**
     * Gets a collection of {@link Proposition}s
     * 
     * @return Collection of <code>Proposition</code>
     */
    public Collection<RuleProposition> getPropositions() {
        return this.propositionMap.values();
    }
    
    /**
     * @param propositionMap the propositionMap to set
     */
    public void setPropositionMap(Map<String, RuleProposition> propositionMap) {
        this.propositionMap = propositionMap;
    }
    /**
     * 
     * This method stores the proposition in the container
     * 
     * @param proposition A proposition
     */
    public void addProposition(RuleProposition proposition) {
        propositionMap.put(proposition.getPropositionName(), proposition);
    }
    /**
     * 
     * This method retrieves a proposition from the container based on the proposition name
     * 
     * @param propositionName
     * @return
     */
    public RuleProposition getProposition(String propositionName) {
        return propositionMap.get(propositionName);
    }
    /**
     * @return the result
     */
    public Boolean getRuleResult() {
        return ruleResult;
    }
    /**
     * @param result the result to set
     */
    public void setRuleResult(Boolean result) {
        this.ruleResult = result;
    }

    public Integer getSize() {
        return propositionMap.size();
    }
    
    /**
     * @return the report
     */
    public RuleReport getRuleReport() {
        return ruleReport;
    }

    /**
     * @param report the report to set
     */
    public void setRuleReport(RuleReport ruleReport) {
        this.ruleReport = ruleReport;
    }
    
    /**
     * Gets default override reporting behaviour.
     * 
     * @return True to override; otherwise false 
     */
	public Boolean getOverrideReport() {
		return overrideReport;
	}
	
	/**
     * Sets the override default reporting behaviour.
	 * 
	 * @param overrideReport True to override; otherwise false 
	 */
	public void setOverrideReport(Boolean overrideReport) {
		this.overrideReport = overrideReport;
	}
}
