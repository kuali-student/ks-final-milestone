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
package org.kuali.student.rules.statement;

import java.util.HashMap;
import java.util.Map;

/**
 * PropositionContainer is a representation of the functional business rule and is composed of the
 * individual propositions in the rule and the boolean operators that join them
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 *
 */
public class PropositionContainer {

    protected String functionalRuleString;
    protected Map<String,Proposition> propositionMap = new HashMap<String, Proposition>();
    protected Boolean ruleResult = false;
    
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
    public Map<String, Proposition> getPropositionMap() {
        return propositionMap;
    }
    /**
     * @param propositionMap the propositionMap to set
     */
    public void setPropositionMap(Map<String, Proposition> propositionMap) {
        this.propositionMap = propositionMap;
    }
    /**
     * 
     * This method stores the proposition in the container
     * 
     * @param propositionName
     * @param proposition
     */
    public void setProposition(String propositionName, Proposition proposition) {
        propositionMap.put(propositionName, proposition);
    }
    /**
     * 
     * This method retrieves a proposition from the container based on the proposition name
     * 
     * @param propositionName
     * @return
     */
    public Proposition getProposition(String propositionName) {
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
}
