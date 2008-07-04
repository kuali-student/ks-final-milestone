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
package org.kuali.student.rules.common.entity;

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
