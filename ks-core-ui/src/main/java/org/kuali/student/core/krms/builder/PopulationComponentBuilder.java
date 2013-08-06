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
package org.kuali.student.core.krms.builder;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.student.core.krms.dto.KSPropositionEditor;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.service.PopulationService;
import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Student Team
 */
public class PopulationComponentBuilder implements ComponentBuilder<KSPropositionEditor> {

    private PopulationService populationService;

    @Override
    public List<String> getComponentIds() {
        return null;
    }

    @Override
    public void resolveTermParameters(KSPropositionEditor propositionEditor, Map<String, String> termParameters) {
        String populationId = termParameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_POPULATION_KEY);
        if (populationId != null) {
            try {
                PopulationInfo populationInfo = this.getPopulationService().getPopulation(populationId, ContextUtils.getContextInfo());
                propositionEditor.setPopulation(populationInfo);
            } catch (Exception e) {
                throw new RuntimeException("Could not load population", e);
            }

        }
    }

    @Override
    public Map<String, String> buildTermParameters(KSPropositionEditor propositionEditor) {
        Map<String, String> termParameters = new HashMap<String, String>();
        if (propositionEditor.getPopulation() != null){
            termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_POPULATION_KEY, propositionEditor.getPopulation().getId());
        }

        return termParameters;
    }

    @Override
    public void onSubmit(KSPropositionEditor propositionEditor) {

    }

    @Override
    public void validate(KSPropositionEditor propositionEditor) {

    }

    private PopulationService getPopulationService() {
        if(populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE, "PopulationService"));
        }
        return this.populationService;
    }
}
