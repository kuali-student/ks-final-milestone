/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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
package org.kuali.student.enrollment.class2.population.keyvalue;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.r2.core.population.service.PopulationService;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class holds the list of rule agenda ids to their rule names
 *
 * @author Kuali Student
 */
public class PopulationRuleAgendaKeyValues extends UifKeyValuesFinderBase implements Serializable {
    private static final long serialVersionUID = 1L;

    private transient PopulationService populationService = null;


    /**
     * Creates a list of KeyValues maping the agenda id to the rule name
     *
     * @param model - The calling view model
     * @return List of all population rule KeyValues
     */
    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        // The complete list of Rule Types
        String ruleTypes[] = PopulationServiceConstants.POPULATION_RULE_TYPE_KEYS;

        // Grab Rule entries for each Rule Type
        for (int j = 0; j < ruleTypes.length; j++) {
            try {

                // Create a keyValue entry for each Rule found for the type
                ArrayList<String> populationRules = (ArrayList<String>) getPopulationService().getPopulationRuleIdsByType(ruleTypes[j], ContextUtils.createDefaultContextInfo());
                for (int i = 0; i < populationRules.size(); i++) {
                    PopulationRuleInfo ruleInfo = getPopulationService().getPopulationRule(populationRules.get(i), ContextUtils.createDefaultContextInfo());
                    if (ruleInfo.getAgendaIds() != null && ruleInfo.getAgendaIds().size() > 0)
                        keyValues.add(new ConcreteKeyValue(ruleInfo.getAgendaIds().get(0), ruleInfo.getName()));
                }

            } catch (Exception e) {
                throw new RuntimeException("Error getting PopulationRuleInfo", e);
            }
        }

        return keyValues;
    }

    /**
     * Stores and retrieves the PopulationService for the page
     *
     * @return The population service
     */
    private PopulationService getPopulationService() {
        if (populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE, "PopulationService"));
        }
        return this.populationService;
    }
}
