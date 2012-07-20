/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by Daniel on 7/12/12
 */
package org.kuali.student.enrollment.class2.population.dto;

import org.kuali.student.r2.common.util.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;

import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class PopulationWrapper implements Serializable {
    private String id;
    private PopulationInfo populationInfo; //The core info (name+description+meta)
    private PopulationRuleInfo  populationRuleInfo;
    private String keyword;
    private String operationType;
    private boolean createByRule;
    private boolean inCreateMode;
    private boolean enableCreateButton;

    public PopulationWrapper(){
        createByRule = true;
        inCreateMode = true;
        enableCreateButton = true;
        operationType = PopulationServiceConstants.POPULATION_RULE_TYPE_UNION_KEY;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PopulationInfo getPopulationInfo() {
        return populationInfo;
    }

    public void setPopulationInfo(PopulationInfo populationInfo) {
        this.populationInfo = populationInfo;
    }

    public PopulationRuleInfo getPopulationRuleInfo() {
        return populationRuleInfo;
    }

    public void setPopulationRuleInfo(PopulationRuleInfo populationRuleInfo) {
        this.populationRuleInfo = populationRuleInfo;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public boolean isCreateByRule() {
        return createByRule;
    }

    public void setCreateByRule(boolean createByRule) {
        this.createByRule = createByRule;
    }

    public boolean isInCreateMode() {
        return inCreateMode;
    }

    public void setInCreateMode(boolean inCreateMode) {
        this.inCreateMode = inCreateMode;
    }

    public boolean isEnableCreateButton() {
        return enableCreateButton;
    }

    public void setEnableCreateButton(boolean enableCreateButton) {
        this.enableCreateButton = enableCreateButton;
    }
}
