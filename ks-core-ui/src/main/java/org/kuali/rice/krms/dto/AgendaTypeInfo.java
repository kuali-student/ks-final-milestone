/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.krms.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author Kuali Student Team
 */
public class AgendaTypeInfo implements Serializable {

    private String id;
    private String type;
    private String description;
    private List<RuleTypeInfo> ruleTypes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RuleTypeInfo> getRuleTypes() {
        return ruleTypes;
    }

    public void setRuleTypes(List<RuleTypeInfo> ruleTypes) {
        this.ruleTypes = ruleTypes;
    }
}
