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

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.common.dto.TypeInfo;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class PopulationRuleTypeKeyValues extends UifKeyValuesFinderBase implements Serializable {

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        // Draft keyValues.add(new ConcreteKeyValue(PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY, "Explicitly defined"));
        // Draft keyValues.add(new ConcreteKeyValue(PopulationServiceConstants.POPULATION_RULE_TYPE_SEARCH_KEY, "Search-based"));
        keyValues.add(new ConcreteKeyValue(PopulationServiceConstants.POPULATION_RULE_TYPE_RULE_KEY, "Rule-based"));
        keyValues.add(new ConcreteKeyValue(PopulationServiceConstants.POPULATION_RULE_TYPE_UNION_KEY, "Union"));
        keyValues.add(new ConcreteKeyValue(PopulationServiceConstants.POPULATION_RULE_TYPE_INTERSECTION_KEY, "Intersection"));
        keyValues.add(new ConcreteKeyValue(PopulationServiceConstants.POPULATION_RULE_TYPE_EXCLUSION_KEY, "Exclusion"));

        return keyValues;
    }
}