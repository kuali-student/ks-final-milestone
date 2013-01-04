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

import org.kuali.student.r2.core.population.dto.PopulationInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class PopulationWrapper implements Serializable {
    private PopulationInfo info; //The core info (name+description+meta)

    private String populationRuleTypeKey; //Core/Union/Intersection

    private String referencePopulationId; //For Exclusion type only
    private String referencePopulationName;//Read only - Can this be replaced with valueFinder?

    private String populationRuleId; //For Core only - the id of the related Core population rule
    private String populationRuleName; //Read only - Can this be replaced with valueFinder?

    List<ChildPopulation> childPopulations; //List of child properties

    public class ChildPopulation{
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public List<ChildPopulation> getChildPopulations() {
        if(null == childPopulations){
            childPopulations = new ArrayList<ChildPopulation>();
        }
        return childPopulations;
    }

    public void setChildPopulations(List<ChildPopulation> childPopulations) {
        this.childPopulations = childPopulations;
    }

    public PopulationInfo getInfo() {
        return info;
    }

    public void setInfo(PopulationInfo info) {
        this.info = info;
    }

    public String getPopulationRuleId() {
        return populationRuleId;
    }

    public void setPopulationRuleId(String populationRuleId) {
        this.populationRuleId = populationRuleId;
    }

    public String getPopulationRuleName() {
        return populationRuleName;
    }

    public void setPopulationRuleName(String populationRuleName) {
        this.populationRuleName = populationRuleName;
    }

    public String getPopulationRuleTypeKey() {
        return populationRuleTypeKey;
    }

    public void setPopulationRuleTypeKey(String populationRuleTypeKey) {
        this.populationRuleTypeKey = populationRuleTypeKey;
    }

    public String getReferencePopulationId() {
        return referencePopulationId;
    }

    public void setReferencePopulationId(String referencePopulationId) {
        this.referencePopulationId = referencePopulationId;
    }

    public String getReferencePopulationName() {
        return referencePopulationName;
    }

    public void setReferencePopulationName(String referencePopulationName) {
        this.referencePopulationName = referencePopulationName;
    }

}
