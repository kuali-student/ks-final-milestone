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

public class BusinessRuleContainer {
    private final String namespace;
    private final String description;
    private final List<BusinessRule> businessRuleList = new ArrayList<BusinessRule>();;

    public BusinessRuleContainer(String namespace, String description) {
        super();
        this.namespace = namespace;
        this.description = description;
    }

    public List<BusinessRule> getBusinessRules() {
        return businessRuleList;
    }

    public void addBusinessRule(BusinessRule businessRule) {
        this.businessRuleList.add(businessRule);
    }

    public String getNamespace() {
        return namespace;
    }

    public String getDescription() {
        return description;
    }
}
