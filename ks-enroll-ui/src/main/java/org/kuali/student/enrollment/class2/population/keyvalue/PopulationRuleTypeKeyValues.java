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
 * Created by mharmath on 7/13/12
 */
package org.kuali.student.enrollment.class2.population.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class does a lookup of all operation types (including rule based)
 *
 * @author Kuali Student Team
 */
public class PopulationRuleTypeKeyValues extends UifKeyValuesFinderBase implements Serializable {

    /**
     *
     * @return List of all population operations KeyValues
     */
    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        keyValues.add(new ConcreteKeyValue(PopulationServiceConstants.POPULATION_RULE_TYPE_RULE_KEY, "Rule-based"));
        keyValues.add(new ConcreteKeyValue(PopulationServiceConstants.POPULATION_RULE_TYPE_UNION_KEY, "Union"));
        keyValues.add(new ConcreteKeyValue(PopulationServiceConstants.POPULATION_RULE_TYPE_INTERSECTION_KEY, "Intersection"));
        keyValues.add(new ConcreteKeyValue(PopulationServiceConstants.POPULATION_RULE_TYPE_EXCLUSION_KEY, "Exclusion"));

        return keyValues;
    }
}