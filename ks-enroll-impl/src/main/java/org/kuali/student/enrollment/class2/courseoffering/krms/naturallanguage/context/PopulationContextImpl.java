/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.enrollment.class2.courseoffering.krms.naturallanguage.context;

import org.kuali.rice.core.api.exception.RiceIllegalStateException;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.krms.naturallanguage.TermParameterTypes;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.service.PopulationService;

import javax.xml.namespace.QName;
import java.util.Map;


/**
 * This class creates the template context for Class Standing.
 */
public class PopulationContextImpl extends BasicContextImpl {

    private PopulationService populationService;

	public final static String POPULATION_TOKEN = "population";


    public void setPopulationService(PopulationService populationService) {
        this.populationService = populationService;
    }
    private PopulationService getPopulationService() {
        if(populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE, "PopulationService"));
        }
        return this.populationService;
    }
	private PopulationInfo getClassStanding(String populationId, ContextInfo context)  {
		if (populationId == null) {
			return null;
		}
		try {

			return  this.getPopulationService().getPopulation(populationId, context);
		} catch (Exception e) {
                    throw new RiceIllegalStateException (e);
		}
	}
    /**
     * Creates the context map (template data) for the requirement component.
     *
     * @param parameters
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException Creating context map fails
     */
    @Override
    public Map<String, Object> createContextMap(Map<String, Object> parameters) {
        ContextInfo contextInfo = ContextUtils.getContextInfo();
        Map<String, Object> contextMap = super.createContextMap(parameters);

        String populationId = (String) parameters.get(TermParameterTypes.POPULATION_KEY.getId());

        if (populationId != null) {
            PopulationInfo populationInfo = this.getClassStanding(populationId, contextInfo);
            contextMap.put(POPULATION_TOKEN, populationInfo);
        }

        return contextMap;
    }
}
