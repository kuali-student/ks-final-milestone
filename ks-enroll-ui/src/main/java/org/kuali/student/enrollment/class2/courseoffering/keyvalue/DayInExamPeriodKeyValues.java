/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by David Yin on 3/8/14
 */
package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class retrieves a complete list of days of exam period and creates a KeyValue for it.
 *
 * @author Kuali Student Team
 */
public class DayInExamPeriodKeyValues extends UifKeyValuesFinderBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        CourseOfferingManagementForm form = (CourseOfferingManagementForm)model;

        if (form.getExamPeriodDays() > 0) {
            for(int day=1; day<=form.getExamPeriodDays(); day++) {
                String key = "Day " + Integer.toString(day);
                String value = Integer.toString(day);
                keyValues.add(new ConcreteKeyValue(value, key));
            }
        }

        return keyValues;
    }
}
