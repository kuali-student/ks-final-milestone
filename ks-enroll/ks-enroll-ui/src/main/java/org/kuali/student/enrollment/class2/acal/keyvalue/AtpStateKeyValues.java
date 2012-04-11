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
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.state.dto.StateInfo;
import org.kuali.student.mock.utilities.TestHelper;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class AtpStateKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient AcademicCalendarService acalService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        CalendarSearchForm form = (CalendarSearchForm)model;
        String atpType = form.getCalendarType();

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        //TODO:Build real context.
        ContextInfo context = TestHelper.getContext1();


        try {
            List<StateInfo> states = new ArrayList<StateInfo>();

            if(atpType.equals(CalendarConstants.HOLIDAYCALENDER)){
                states = getAcalService().getHolidayCalendarStates(context);
            }else if(atpType.equals(CalendarConstants.ACADEMICCALENDER)) {
                states = getAcalService().getAcademicCalendarStates(context);
            }else if(atpType.equals(CalendarConstants.TERM)){
                states = getAcalService().getTermStates(context);
            }

            for (StateInfo state : states) {
                ConcreteKeyValue keyValue = new ConcreteKeyValue();
                keyValue.setKey(state.getKey());
                keyValue.setValue(state.getName());
                keyValues.add(keyValue);
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return keyValues;
    }

    public AcademicCalendarService getAcalService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "acal", "AcademicCalendarService"));
        }
        return this.acalService;
    }
}
