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

import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    private PopulationRuleInfo populationRuleInfo;
    private String keyword;
    private String operationType;
    private String operationTypeText;
    private String populationRuleTypeKeyName;
    private String populationStateKeyName;
    private boolean createByRule;
    private boolean enableCreateButton;
    private String pageTitle;

    private List<PopulationInfo> childPopulations;
    private PopulationInfo referencePopulation;

    public PopulationWrapper(){
        createByRule = true;
        enableCreateButton = true;
        operationType = PopulationServiceConstants.POPULATION_RULE_TYPE_UNION_KEY;
        pageTitle="Create a New Population";
        populationInfo = new PopulationInfo();
        populationRuleInfo = new PopulationRuleInfo();
        childPopulations = new ArrayList<PopulationInfo>();
        referencePopulation = new PopulationInfo();
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

    public String getOperationTypeText() {
        return operationTypeText;
    }

    public void setOperationTypeText(String operationTypeText) {
        this.operationTypeText = operationTypeText;
    }

    public String getPopulationRuleTypeKeyName() {
        return populationRuleTypeKeyName;
    }

    public void setPopulationRuleTypeKeyName(String populationRuleTypeKeyName) {
        this.populationRuleTypeKeyName = populationRuleTypeKeyName;
    }

    public String getPopulationStateKeyName() {
        return populationStateKeyName;
    }

    public void setPopulationStateKeyName(String populationStateKeyName) {
        this.populationStateKeyName = populationStateKeyName;
    }

    public boolean isCreateByRule() {
        return createByRule;
    }

    public void setCreateByRule(boolean createByRule) {
        this.createByRule = createByRule;
    }

    public boolean isEnableCreateButton() {
        return enableCreateButton;
    }

    public void setEnableCreateButton(boolean enableCreateButton) {
        this.enableCreateButton = enableCreateButton;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public List<PopulationInfo> getChildPopulations() {
        return childPopulations;
    }

    public void setChildPopulations(List<PopulationInfo> childPopulations) {
        this.childPopulations = childPopulations;
    }

    public PopulationInfo getReferencePopulation() {
        return referencePopulation;
    }

    public void setReferencePopulation(PopulationInfo referencePopulation) {
        this.referencePopulation = referencePopulation;
    }


}
