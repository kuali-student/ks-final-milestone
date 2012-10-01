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

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.PopulationServiceConstants;
import org.kuali.student.r2.common.util.constants.StateServiceConstants;
import org.kuali.student.r2.core.state.dto.StateInfo;
import org.kuali.student.r2.core.state.service.StateService;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class PopulationStateKeyValues extends UifKeyValuesFinderBase implements Serializable {
    private StateService stateService;
    List<KeyValue> keyValues = new ArrayList<KeyValue>();

    final Logger LOG = Logger.getLogger(PopulationStateKeyValues.class);

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<StateInfo> stateInfos = null;
        try {
            stateService = getStateService();
            stateInfos = stateService.getStatesByLifecycle(PopulationServiceConstants.POPULATION_LIFECYCLE_KEY, getContextInfo());

            if (stateInfos != null) {
                for (StateInfo stateInfo : stateInfos) {
                    keyValues.add(new ConcreteKeyValue(stateInfo.getKey(), stateInfo.getName()));
                }
                keyValues.add(new ConcreteKeyValue("both", "Both"));
            }
        } catch (InvalidParameterException e) {
            LOG.error("Error getting PopulationInfo state: Invalid Parameter ", e);
            throw new RuntimeException(e);
        } catch (MissingParameterException e) {
            LOG.error("Error getting PopulationInfo state: Missing Parameter ", e);
            throw new RuntimeException(e);
        } catch (OperationFailedException e) {
            LOG.error("Error getting PopulationInfo state: Operation Failed ", e);
            throw new RuntimeException(e);
        } catch (PermissionDeniedException e) {
            LOG.error("Error getting PopulationInfo state: Permission Denied ", e);
            throw new RuntimeException(e);
        } catch (DoesNotExistException e) {
            LOG.error("Error getting PopulationInfo state: Does Not Exist ", e);
            throw new RuntimeException(e);
        }

        /*  Hardcode the states
        keyValues.add(new ConcreteKeyValue(PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY, "Active"));
        keyValues.add(new ConcreteKeyValue(PopulationServiceConstants.POPULATION_INACTIVE_STATE_KEY, "Inactive"));
        keyValues.add(new ConcreteKeyValue("kuali.population.population.state.both", "Both"));
        */
        return keyValues;
    }

    private StateService getStateService() {
        if (stateService == null) {
            stateService = (StateService) GlobalResourceLoader.getService(new QName(StateServiceConstants.NAMESPACE,
                    StateServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return stateService;
    }

    public ContextInfo getContextInfo() {
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setAuthenticatedPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        contextInfo.setPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(Locale.getDefault().getLanguage());
        localeInfo.setLocaleRegion(Locale.getDefault().getCountry());
        contextInfo.setLocale(localeInfo);
        return contextInfo;
    }
}
