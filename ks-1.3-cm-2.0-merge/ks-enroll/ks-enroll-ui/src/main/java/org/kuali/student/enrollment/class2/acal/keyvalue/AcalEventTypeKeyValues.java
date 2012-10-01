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
 */

package org.kuali.student.enrollment.class2.acal.keyvalue;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.AcalEventWrapper;
import org.kuali.student.enrollment.class2.acal.form.AcademicCalendarForm;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.dto.TypeInfo;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AcalEventTypeKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient AcademicCalendarService acalService;

    private static List<TypeInfo> acalEventTypes;

    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        List<String> alreadyAddedTypes = new ArrayList();

        AcademicCalendarForm acalForm = (AcademicCalendarForm)model;

        for (AcalEventWrapper eventWrapper : acalForm.getEvents()){
            alreadyAddedTypes.add(eventWrapper.getEventTypeKey());
        }

        //Hard code "Select Event Type"
        ConcreteKeyValue topKeyValue = new ConcreteKeyValue();
        topKeyValue.setKey("");
        topKeyValue.setValue("Select Event Type");
        keyValues.add(topKeyValue);

        try {
            List<TypeInfo> types = getAcalEventTypes();
            for (TypeInfo type : types) {
                if (!alreadyAddedTypes.contains(type.getKey())){
                    ConcreteKeyValue keyValue = new ConcreteKeyValue();
                    keyValue.setKey(type.getKey());
                    keyValue.setValue(type.getName());
                    keyValues.add(keyValue);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return keyValues;
    }

    private List<TypeInfo> getAcalEventTypes() throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        if(acalEventTypes == null) {
            //TODO:Build real context.
            ContextInfo context = TestHelper.getContext1();

            acalEventTypes = Collections.unmodifiableList(getAcalService().getAcalEventTypes(context));
        }
        return acalEventTypes;  //To change body of created methods use File | Settings | File Templates.
    }

    public AcademicCalendarService getAcalService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.acalService;
    }
}
