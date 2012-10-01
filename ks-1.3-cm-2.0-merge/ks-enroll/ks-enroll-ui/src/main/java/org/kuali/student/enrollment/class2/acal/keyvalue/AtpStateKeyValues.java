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
 * Created by Li Pan on 3/6/12
 */
package org.kuali.student.enrollment.class2.acal.keyvalue;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.form.CalendarSearchForm;
import org.kuali.student.enrollment.class2.acal.util.CalendarConstants;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.state.dto.StateInfo;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
//Core slice class.
@Deprecated
public class AtpStateKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient AcademicCalendarService acalService;

    private static List<StateInfo> holidayStates;

    private static List<StateInfo> acalStates;

    private static List<StateInfo> termStates;


    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        CalendarSearchForm form = (CalendarSearchForm)model;
        String atpType = form.getCalendarType();

        List<KeyValue> keyValues = new ArrayList<KeyValue>();


        try {
            List<StateInfo> states = null;

            if(atpType.equals(CalendarConstants.HOLIDAYCALENDER)){
                states = getHolidayStates();
            }else if(atpType.equals(CalendarConstants.ACADEMICCALENDER)) {
                states = getAcalStates();
            }else if(atpType.equals(CalendarConstants.TERM)){
                states = getTermStates();
            }

            for (StateInfo state : states) {
                ConcreteKeyValue keyValue = new ConcreteKeyValue();
                keyValue.setKey(state.getKey());
                keyValue.setValue(state.getName());
                keyValues.add(keyValue);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return keyValues;
    }

    public List<StateInfo> getHolidayStates() throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        if(holidayStates == null) {
            //TODO:Build real context.
            ContextInfo context = TestHelper.getContext1();

            holidayStates = Collections.unmodifiableList(getAcalService().getHolidayCalendarStates(context));
        }
        return holidayStates;
    }

    public List<StateInfo> getAcalStates() throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        if(acalStates == null) {
            //TODO:Build real context.
            ContextInfo context = TestHelper.getContext1();

            acalStates = Collections.unmodifiableList(getAcalService().getAcademicCalendarStates(context));
        }

        return acalStates;
    }

    public List<StateInfo> getTermStates() throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        if(termStates == null) {
            //TODO:Build real context.
            ContextInfo context = TestHelper.getContext1();

            termStates = Collections.unmodifiableList(getAcalService().getTermStates(context));
        }
        return termStates;
    }

    public AcademicCalendarService getAcalService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "acal", "AcademicCalendarService"));
        }
        return this.acalService;
    }
}
