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
 * Created by David Yin on 7/19/12
 */
package org.kuali.student.enrollment.class2.population.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.class2.population.dto.PopulationWrapper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.r2.core.population.service.PopulationService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class performs Population Inquiries
 *
 * @author Kuali Student Team
 */
public class PopulationWrapperInquirableImpl extends InquirableImpl {
    private static final long serialVersionUID = 1L;
    private transient PopulationService populationService = null;

    @Override
    public PopulationWrapper retrieveDataObject(Map<String, String> parameters) {
        String populationInfoId = parameters.get("id");

        ContextInfo context = ContextUtils.createDefaultContextInfo();

        try {
            populationService = getPopulationService();
            PopulationInfo populationInfo = populationService.getPopulation(populationInfoId, context);
            PopulationRuleInfo populationRuleInfo = populationService.getPopulationRuleForPopulation(populationInfoId, context);

            PopulationWrapper populationWrapper = new PopulationWrapper();
            populationWrapper.setPopulationInfo(populationInfo);
            populationWrapper.setPopulationRuleInfo(populationRuleInfo);

            List<PopulationInfo> childPopulations = new ArrayList<PopulationInfo>();
            for (String childPopulationId : populationRuleInfo.getChildPopulationIds())  {
                PopulationInfo childPopulation = populationService.getPopulation(childPopulationId, context);
                if (childPopulation != null) {
                    childPopulations.add(childPopulation);
                }
            }
            populationWrapper.setChildPopulations(childPopulations);

            if (populationRuleInfo.getReferencePopulationId() != null) {
                PopulationInfo referencePopulation = populationService.getPopulation(populationRuleInfo.getReferencePopulationId(), context);
                if (referencePopulation != null) {
                    populationWrapper.setReferencePopulation(referencePopulation);
                }
            }

            return populationWrapper;

        } catch (Exception e) {
            throw new RuntimeException("Error retrieving Population", e);
        }
    }

    private PopulationService getPopulationService() {
        if(populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE, "PopulationService"));
        }
        return this.populationService;
    }

}
