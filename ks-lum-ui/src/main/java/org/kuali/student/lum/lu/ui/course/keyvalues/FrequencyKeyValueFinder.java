/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.course.keyvalues;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;

/**
 * 
 * This is the helper class for CourseView
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 * 
 */

public class FrequencyKeyValueFinder extends UifKeyValuesFinderBase {

    public static final String PER_DAY_FREQUENCY = "per day";
    public static final String PER_MONTH_FREQUENCY = "per month";
    public static final String PER_WEEK_FREQUENCY = "per week";

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> labels = new ArrayList<KeyValue>();
        labels.add(new ConcreteKeyValue(AtpServiceConstants.DURATION_DAY_TYPE_KEY, PER_DAY_FREQUENCY));
        labels.add(new ConcreteKeyValue(AtpServiceConstants.DURATION_WEEK_TYPE_KEY, PER_WEEK_FREQUENCY));
        labels.add(new ConcreteKeyValue(AtpServiceConstants.DURATION_MONTH_TYPE_KEY, PER_MONTH_FREQUENCY));
        return labels;
    }
    
}
