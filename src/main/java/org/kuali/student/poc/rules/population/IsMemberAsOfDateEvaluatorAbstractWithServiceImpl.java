/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by mahtabme on 12/10/13
 */
package org.kuali.student.poc.rules.population;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.service.PopulationService;
import javax.xml.namespace.QName;

/**
 * This class handles a lot of basic functionalities with associating a service to the evaluator.
 *
 * @author Kuali Student Team
 */
public abstract class IsMemberAsOfDateEvaluatorAbstractWithServiceImpl implements IsMemberAsOfDateEvaluator {

    private PopulationService populationService;

    public PopulationService getPopulationService() {
        if (populationService == null) {
            QName qname = new QName(PopulationServiceConstants.NAMESPACE,
                    PopulationServiceConstants.SERVICE_NAME_LOCAL_PART);
            populationService = GlobalResourceLoader.getService(qname);
        }
        return populationService;
    }

    public void setPopulationService(PopulationService populationService) {
        this.populationService = populationService;
    }

}
