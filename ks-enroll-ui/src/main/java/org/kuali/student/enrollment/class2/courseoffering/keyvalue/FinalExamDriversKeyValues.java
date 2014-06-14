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
package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class provides a key value finder for Waitlist types
 *
 * @author Kuali Student Team
 */
public class FinalExamDriversKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        List<String> finalExamDriverTypes  =  new ArrayList<String>(Arrays.asList(LuServiceConstants.ALL_EXAM_DRIVER_KEYS));

        Collections.sort(finalExamDriverTypes);

        //add values
        for(String finalExamDriverType: finalExamDriverTypes) {
            if (finalExamDriverType.equals(LuServiceConstants.LU_EXAM_DRIVER_AO_KEY)) {
                keyValues.add(new ConcreteKeyValue(LuServiceConstants.LU_EXAM_DRIVER_AO_KEY, "Final Exam Per Activity Offering"));
            } else if (finalExamDriverType.equals(LuServiceConstants.LU_EXAM_DRIVER_CO_KEY)) {
                keyValues.add(new ConcreteKeyValue(LuServiceConstants.LU_EXAM_DRIVER_CO_KEY, "Final Exam Per Course Offering"));
            }
        }

        return keyValues;
    }

}