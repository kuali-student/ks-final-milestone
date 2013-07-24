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
package org.kuali.student.enrollment.class1.krms.builder;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;
import org.kuali.student.enrollment.class2.population.dto.PopulationWrapper;
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
public class PopulationComponentBuilder implements ComponentBuilder<EnrolPropositionEditor> {

    private PopulationService populationService;

    @Override
    public List<String> getComponentIds() {
        return null;
    }

    @Override
    public void resolveTermParameters(EnrolPropositionEditor propositionEditor, Map<String, String> termParameters) {
        String populationId = termParameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_POPULATION_KEY);
        if (populationId != null) {
            try {
                PopulationInfo populationInfo = this.getPopulationService().getPopulation(populationId, ContextUtils.getContextInfo());
                PopulationWrapper populationWrapper = new PopulationWrapper();
                propositionEditor.setPopulationWrapper(populationWrapper);
                propositionEditor.getPopulationWrapper().setId(populationInfo.getId());
                propositionEditor.getPopulationWrapper().setPopulationInfo(populationInfo);
                propositionEditor.setPopulation(populationInfo.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public Map<String, String> buildTermParameters(EnrolPropositionEditor propositionEditor) {
        Map<String, String> termParameters = new HashMap<String, String>();
        if (propositionEditor.getPopulation() != null){

            termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_POPULATION_KEY, propositionEditor.getPopulationWrapper().getId());
        }

        return termParameters;
    }

    @Override
    public void onSubmit(EnrolPropositionEditor propositionEditor) {

    }

    @Override
    public void validate(EnrolPropositionEditor propositionEditor) {

    }

    private PopulationService getPopulationService() {
        if(populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE, "PopulationService"));
        }
        return this.populationService;
    }
}
