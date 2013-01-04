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
 * Created by David Yin on 7/18/12
 */
package org.kuali.student.enrollment.class2.population.keyvalue;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.constants.StateServiceConstants;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class gets all the states for populations
 *
 * @author Kuali Student Team
 */
public class PopulationStateKeyValues extends UifKeyValuesFinderBase implements Serializable {
    private static final long serialVersionUID = 1L;
    private transient StateService stateService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        try {
            List<StateInfo> stateInfos = getStateService().getStatesByLifecycle(PopulationServiceConstants.POPULATION_LIFECYCLE_KEY, ContextUtils.createDefaultContextInfo());

            if (stateInfos != null) {
                for (StateInfo stateInfo : stateInfos) {
                    keyValues.add(new ConcreteKeyValue(stateInfo.getKey(), stateInfo.getName()));
                }
                keyValues.add(new ConcreteKeyValue("both", "Both"));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error getting PopulationInfo state", e);
        }

        return keyValues;
    }

    private StateService getStateService() {
        if (stateService == null) {
            stateService = (StateService) GlobalResourceLoader.getService(new QName(StateServiceConstants.NAMESPACE,
                    StateServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return stateService;
    }

}
