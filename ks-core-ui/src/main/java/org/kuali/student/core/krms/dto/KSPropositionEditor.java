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
package org.kuali.student.core.krms.dto;

import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.population.dto.PopulationInfo;

/**
 * @author Kuali Student Team
 */
public class KSPropositionEditor extends PropositionEditor {

    private static final long serialVersionUID = 1L;

    private OrgInfo orgInfo;
    private Integer duration;
    private String durationType;
    private PopulationInfo population;

    public KSPropositionEditor(){
        super();
    }

    public KSPropositionEditor(PropositionDefinitionContract definition) {
        super(definition);
    }

    public void clear(){
        super.clear();
        this.orgInfo = null;
        this.duration = null;
        this.durationType = null;
    }

    public OrgInfo getOrgInfo() {
        return orgInfo;
    }

    public void setOrgInfo(OrgInfo orgInfo) {
        this.orgInfo = orgInfo;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDurationType() {
        return durationType;
    }

    public void setDurationType(String durationType) {
        this.durationType = durationType;
    }

    public PopulationInfo getPopulation() {
        return population;
    }

    public void setPopulation(PopulationInfo population) {
        this.population = population;
    }
}
