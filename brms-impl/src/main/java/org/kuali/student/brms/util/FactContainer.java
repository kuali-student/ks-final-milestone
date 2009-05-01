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
package org.kuali.student.brms.util;

import java.util.Map;

import org.kuali.student.brms.internal.common.statement.PropositionContainer;
import org.kuali.student.brms.rulemanagement.dto.RulePropositionInfo;

public class FactContainer implements java.io.Serializable {
    /** Class serial version uid */
    private static final long serialVersionUID = 1L;

    private String id;
    private String anchor;
    private String anchorTypeKey;
    Map<String, RulePropositionInfo> propositionMap;
    private PropositionContainer propositionContainer = new PropositionContainer();
    private Map<String, ?> factMap;
    private State state = State.INIT;
    
    public enum State {INIT,DONE};

    public FactContainer(String id, String anchor, String anchorTypeKey, Map<String, RulePropositionInfo> propositionMap, Map<String, ?> factMap) {
        this.id = id;
        this.anchor = anchor;
        this.anchorTypeKey = anchorTypeKey;
        this.propositionMap = propositionMap;
        this.factMap = factMap;
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getAnchor() {
        return this.anchor;
    }
    
    public String getAnchorTypeKey() {
        return this.anchorTypeKey;
    }
    
    public Map<String, RulePropositionInfo> getPropositionMap() {
    	return this.propositionMap;
    }
    
    public Map<String, ?> getFactMap() {
        return factMap;
    }
    
    public PropositionContainer getPropositionContainer() {
        return propositionContainer;
    }
    
    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }
    
    public String toString() {
    	return "FactContainer[id=" + this.id + ", anchor='" + this.anchor + 
    		"', anchorTypeKey='"+this.anchorTypeKey+"']";
    }
}
