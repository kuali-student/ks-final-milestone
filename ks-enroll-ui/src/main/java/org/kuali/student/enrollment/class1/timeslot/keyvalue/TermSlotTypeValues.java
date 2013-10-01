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
 */
package org.kuali.student.enrollment.class1.timeslot.keyvalue;

import edu.emory.mathcs.backport.java.util.Collections;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class1.timeslot.form.TimeSlotForm;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.TypeServiceConstants;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.List;

/**
 * Provides a list keys and values for Time Slot types.
 */
public class TermSlotTypeValues extends UifKeyValuesFinderBase implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(TermSlotTypeValues.class);

    private transient TypeService typeService;

    private ContextInfo contextInfo;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        TimeSlotForm form = (TimeSlotForm)model;
        List<KeyValue> keyValuePairs = form.getTimeslotKeyValues();
        //  Since type data is static while the app is running, only rebuild the list of it doesn't already exist.
        if (form.getTimeslotKeyValues().isEmpty()) {
            List<TypeInfo> typeInfos = null;
            try {
                typeInfos = getTypeService().getTypesForGroupType("SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING", getContextInfo());
            } catch (Exception e) {
                //  Log and return an empty list if the service call to get the TimeSlot types blows up.
                LOGGER.error("Unable to build a list of time slot types.", e);
                return Collections.emptyList();
            }

            keyValuePairs.add(new ConcreteKeyValue("one", "One"));
            keyValuePairs.add(new ConcreteKeyValue("two", "Two"));
            keyValuePairs.add(new ConcreteKeyValue("three", "Three"));
//            keyValuePairs.add(new ConcreteKeyValue(TimeSlotConstants.TimeSlotTypeSelectorWidget.LINE_KEY, TimeSlotConstants.TimeSlotTypeSelectorWidget.LINE_TEXT));
//           keyValuePairs.add(new ConcreteKeyValue(TimeSlotConstants.TypeSlotTypeDropDown.ALL_TERMS_KEY, TimeSlotConstants.TypeSlotTypeDropDown.ALL_TERMS_TEXT));
        }
        return keyValuePairs;
    }

    private ContextInfo getContextInfo() {
        if (contextInfo == null) {
            contextInfo = ContextUtils.createDefaultContextInfo();
        }
        return contextInfo;
    }

    public TypeService getTypeService() {
        if(typeService == null){
            typeService = (TypeService)
                GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return typeService;
    }
}
