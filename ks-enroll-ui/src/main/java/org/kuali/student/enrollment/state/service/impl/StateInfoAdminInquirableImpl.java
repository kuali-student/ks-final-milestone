/**
 * Copyright 2012 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 *
 */
package org.kuali.student.enrollment.state.service.impl;

import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.constants.StateServiceConstants;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;

/**
 * This class 
 *
 * @author Kuali Student Team
 */
public class StateInfoAdminInquirableImpl
        extends InquirableImpl {

    private final static String KEY = "key";
    private transient StateService stateService;
    final Logger logger = Logger.getLogger(StateInfoAdminInquirableImpl.class);

    @Override
    public StateInfo retrieveDataObject(Map<String, String> parameters) {
        try {
            return getStateService().getState(parameters.get(KEY), getContextInfo());
        } catch (DoesNotExistException ex) {
            throw new RuntimeException (ex);
        } catch (InvalidParameterException ex) {
            throw new RuntimeException (ex);
        } catch (MissingParameterException ex) {
            throw new RuntimeException (ex);
        } catch (OperationFailedException ex) {
            throw new RuntimeException (ex);
        } catch (PermissionDeniedException ex) {
            throw new RuntimeException (ex);
        }
    }

    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    public StateService getStateService() {
        if (stateService == null) {
            stateService = (StateService) GlobalResourceLoader.getService(new QName(StateServiceConstants.NAMESPACE,
                    StateServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.stateService;
    }

    private ContextInfo getContextInfo() {
        return ContextBuilder.loadContextInfo();
    }
}
